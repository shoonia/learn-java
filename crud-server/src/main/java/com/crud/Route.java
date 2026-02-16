package com.crud;

import io.javalin.http.Context;
import com.crud.Models.*;

public record Route(Database db) {

  public void createTask(Context ctx) {
    if (ctx.body().isEmpty()) {
      ctx.status(400).json("Request body is empty");
      return;
    }

    var task = ctx.bodyValidator(CreateTaskRequest.class)
        .check(req -> !req.title().isEmpty(), "Title is required")
        .check(req -> !req.details().isEmpty(), "Details is required")
        .check(req -> req.title().length() <= 255, "Title must be less than 255 characters")
        .check(req -> req.details().length() <= 255, "Details must be less than 255 characters")
        .get();

    var createdTask = db.createTask(task.title(), task.details());

    if (createdTask.isPresent()) {
      ctx.status(201).json(createdTask.get());
    } else {
      ctx.status(500).json("Failed to create task");
    }
  }

  public void getTask(Context ctx) {
    int id;
    try {
      id = Integer.parseInt(ctx.pathParam("id"));
    } catch (NumberFormatException e) {
      ctx.status(400).json("Invalid task ID");
      return;
    }

    var task = db.getTask(id);

    if (task.isPresent()) {
      ctx.json(task.get());
    } else {
      ctx.status(404).json("Task not found");
    }
  }

  public void deleteTask(Context ctx) {
    if (ctx.body().isEmpty()) {
      ctx.status(400).json("Request body is empty");
      return;
    }

    var request = ctx.bodyValidator(DeleteTaskRequest.class)
      .check(req -> req.id() > 0, "ID must be a positive integer")
      .check(req -> req.revision() > 0, "Revision must be a positive integer")
      .get();

    var isDeleted = db.deleteTask(request.id(), request.revision());

    if (isDeleted) {
      ctx.status(204);
    } else {
      var isExists = db.isTaskExists(request.id());

      if (isExists) {
        ctx.status(409).json("Task revision mismatch");
      } else {
        ctx.status(404).json("Task not found");
      }
    }
  }

  public void updateTask(Context ctx) {
    if (ctx.body().isEmpty()) {
      ctx.status(400).json("Request body is empty");
      return;
    }

    var task = ctx.bodyValidator(UpdateTaskRequest.class)
      .check(req -> req.id() > 0, "ID must be a positive integer")
      .check(req -> req.revision() > 0, "Revision must be a positive integer")
      .check(req -> !req.title().isEmpty(), "Title is required")
      .check(req -> !req.details().isEmpty(), "Details is required")
      .check(req -> req.title().length() <= 255, "Title must be less than 255 characters")
      .check(req -> req.details().length() <= 255, "Details must be less than 255 characters")
      .get();

    var updatedTask = db.updateTask(
      task.id(),
      task.revision(),
      task.title(),
      task.details()
    );

    if (updatedTask.isPresent()) {
      ctx.json(updatedTask.get());
    } else {
      var isExists = db.isTaskExists(task.id());

      if (isExists) {
        ctx.status(409).json("Task revision mismatch");
      } else {
        ctx.status(404).json("Task not found");
      }
    }
  }

  public void listTasks(Context ctx) {
    int limit = ctx.queryParamAsClass("limit", Integer.class)
      .check(i -> i > 0, "Limit must be a positive integer")
      .check(i -> i < 1000, "Limit must be less than 1000")
      .getOrDefault(50);

    int offset = ctx.queryParamAsClass("offset", Integer.class)
      .check(i -> i >= 0, "Offset must be a non-negative integer")
      .getOrDefault(0);

    var tasks = db.queryTasks(limit, offset);
    var count = db.countTasks();

    var paging = new Paging(limit, offset, count);
    var response = new ListTasksResponse(tasks, paging);

    ctx.json(response);
  }
}
