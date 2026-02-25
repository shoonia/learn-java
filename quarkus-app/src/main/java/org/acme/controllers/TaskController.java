package org.acme.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {

  @GET
  public List<String> getAllItems() {
    return List.of("Laptop", "Mouse", "Keyboard");
  }
}
