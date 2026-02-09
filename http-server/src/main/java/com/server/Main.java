package com.server;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import com.server.Routes.*;

public class Main {
  public static void main(String[] args) {
    Javalin.create(config -> {
              config.jsonMapper(new JavalinJackson());
            })
            .get("redirect", Routes.redirect)
            .get("/random.png", Routes.randomPng)
            .get("/calculate", Routes.calculate)
            .get("/", Routes.root)
            .start(3000);
    }
}
