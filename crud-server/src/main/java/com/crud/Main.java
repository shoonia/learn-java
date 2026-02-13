package com.crud;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class Main {
	public static void main(String[] args) {
		var db = new Database();
		var route = new Route(db);

		Javalin.create(config -> {
							config.jsonMapper(new JavalinJackson());
						})
						.post("/task", route::createTask)
						.get("/task/{id}", route::getTask)
						.start(3000);
	}
}