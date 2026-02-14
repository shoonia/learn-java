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

    if (createdTask.isEmpty()) {
      ctx.status(500).json("Failed to create task");
      return;
    }

    ctx.status(201).json(createdTask.get());
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

    if (task.isEmpty()) {
      ctx.status(404).json("Task not found");
      return;
    }

    ctx.json(task.get());
  }

  public void deleteTask(Context ctx) {
    if (ctx.body().isEmpty()) {
      ctx.status(400).json("Request body is empty");
      return;
    }

    var request = ctx.bodyValidator(DeleteTaskRequest.class)
      .check(req -> req.id() > 0, "ID must be a positive integer")
      .get();

    db.deleteTask(request.id());
    ctx.status(204);
  }

  public void updateTask(Context ctx) {
    if (ctx.body().isEmpty()) {
      ctx.status(400).json("Request body is empty");
      return;
    }

    var task = ctx.bodyValidator(UpdateTaskRequest.class)
      .check(req -> req.id() > 0, "ID must be a positive integer")
      .check(req -> !req.title().isEmpty(), "Title is required")
      .check(req -> !req.details().isEmpty(), "Details is required")
      .check(req -> req.title().length() <= 255, "Title must be less than 255 characters")
      .check(req -> req.details().length() <= 255, "Details must be less than 255 characters")
      .get();

    var updatedTask = db.updateTask(
      task.id(),
      task.title(),
      task.details()
    );

    if (updatedTask.isEmpty()) {
      ctx.status(404).json("Task not found");
      return;
    }

    ctx.status(200).json(updatedTask.get());
  }
}
