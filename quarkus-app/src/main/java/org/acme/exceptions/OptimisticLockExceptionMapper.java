package org.acme.exceptions;

import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class OptimisticLockExceptionMapper implements ExceptionMapper<OptimisticLockException> {

  @Override
  public Response toResponse(OptimisticLockException exception) {
    var errorMessage = Map.of(
      "error", "Conflict",
      "message", "The data you are trying to update has been modified. Please refresh and try again."
    );
    return Response.status(Status.CONFLICT).entity(errorMessage).build();
  }
}