package com.spring.webapp.exceptions;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Map<String, String> handleConflict(ObjectOptimisticLockingFailureException ex) {
    return Map.of(
      "error", "Conflict",
      "message", "The data you are trying to update has been modified. Please refresh and try again."
    );
  }
}