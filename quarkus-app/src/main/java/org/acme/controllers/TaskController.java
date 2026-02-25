package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CreateRequest;
import org.acme.model.Task;
import org.acme.repository.TaskRepository;

@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {

  @Inject
  TaskRepository taskRepository;

  @PUT
  @Transactional
  public Response createTask(
    @Valid
    CreateRequest req
  ) {
    var task = new Task();
    task.title = req.title();
    task.details = req.getDetails().orElse("");
    task.persist();

    return Response.status(Response.Status.CREATED).entity(task).build();
  }
}
