package com.crud;

import io.javalin.http.Context;
import com.crud.Models.*;

public class Route {
  private final Database db;

  public Route(Database db) {
    this.db = db;
  }

  public final void createTask(Context ctx) {
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

    db.createTask(task.title(), task.details());
    ctx.status(201).json("Success");
  }

  public final void getTask(Context ctx) {
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
}
