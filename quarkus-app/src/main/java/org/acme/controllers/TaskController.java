package org.acme.controllers;

import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.*;
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

  @GET
  @Path("/page")
  public Response getPage(
    @QueryParam("limit")
    @Min(0)
    @DefaultValue("0")
    int page,

    @QueryParam("offset")
    @Min(1)
    @Max(1000)
    @DefaultValue("100")
    int limit
  ) {
    var result = taskRepository.findAll().page(Page.of(page, limit));
    var paging = new Paging(
      page,
      limit,
      result.count(),
      result.pageCount()
    );
    var response = new PageResponse(result.list(), paging);

    return Response.ok(response).build();
  }
}
