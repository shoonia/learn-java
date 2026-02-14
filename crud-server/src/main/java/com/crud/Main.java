package com.crud;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class Main {
  public static void main(String[] args) {
    var db = new Database("jdbc:sqlite:target/tasks.db");
    var route = new Route(db);

    db.init();

    Javalin.create(config ->
      config.jsonMapper(new JavalinJackson())
    )
      .post("/task", route::createTask)
      .delete("/task", route::deleteTask)
      .patch("/task", route::updateTask)
      .get("/task/{id}", route::getTask)
      .get("/tasks", route::listTasks)
      .start(3000);
  }
}
