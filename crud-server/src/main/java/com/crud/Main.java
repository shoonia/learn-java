package com.crud;

import com.crud.controllers.TaskController;
import com.crud.repository.TaskRepository;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class Main {
  public static void main(String[] args) {
    var db = new TaskRepository("jdbc:sqlite:target/tasks.db");
    var route = new TaskController(db);

    db.init();

    Javalin.create(config ->
      config.jsonMapper(new JavalinJackson())
    )
      .put("/task", route::createTask)
      .delete("/task", route::deleteTask)
      .patch("/task", route::updateTask)
      .get("/task/{id}", route::getTask)
      .get("/tasks", route::listTasks)
      .start(3000);
  }
}
