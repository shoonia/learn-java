package com.crud;

import io.javalin.http.Context;
import com.crud.Models.*;

public class Route {
	private final Database db;

	public Route(Database db) {
		this.db = db;
	}

	public final void createTask(Context ctx) {
		var task = ctx.bodyAsClass(CreateTaskRequest.class);
		db.createTask(task.title(), task.details());
		ctx.status(201).json("Success");
	}
}
