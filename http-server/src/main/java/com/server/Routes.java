package com.server;

import io.javalin.http.Handler;

import java.util.Map;
import java.util.Random;

public class Routes {
  public static final Handler redirect = ctx -> {
    ctx.status(301).redirect("https://javalin.io/documentation");
  };

  public static final Handler root = ctx -> {
    ctx.result("Hello World");
  };

  public static final Handler randomPng = ctx -> {
    byte[] rgb = new byte[3];
    new Random().nextBytes(rgb);
    byte[] png = new byte[] {
            71, 73, 70, 56, 55, 97, 1, 0, 1, 0, (byte) 128, 1, 0, 0, 0, 0, rgb[0], rgb[1], rgb[2], 44, 0, 0, 0, 0, 1, 0, 1,
            0, 0, 2, 2, 76, 1, 0, 59,
    };

    ctx.contentType("image/png").result(png);
  };

  public static final Handler calculate = ctx -> {
    var a = ctx.queryParam("a");
    var b = ctx.queryParam("b");
    var operator = ctx.queryParam("operator");

    if (a == null || b == null || operator == null) {
      ctx.status(400).result("Missing query parameters");
      return;
    }

    var a1 = Double.parseDouble(a);
    var b1 = Double.parseDouble(b);

    var result = switch (operator) {
      case "add" -> a1 + b1;
      case "subtract" -> a1 - b1;
      case "multiply" -> a1 * b1;
      case "divide" -> {
        if (b1 == 0) {
          ctx.status(400).result("Cannot divide by zero");
          yield null;
        }

        yield a1 / b1;
      }
      default -> {
        ctx.status(400).result("Invalid operator");
        yield null;
      }
    };

    if (result == null) {
      return;
    }

    ctx.json(Map.of("result", result));
  };
}
