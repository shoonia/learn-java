package com.spring.webapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record UpdateRequest(
  @Min(value = 0, message = "id must be greater than or equal to 0")
  @Max(Long.MAX_VALUE)
  long id,

  @Min(value = 0, message = "revision must be greater than or equal to 0")
  @Max(Integer.MAX_VALUE)
  Integer revision,

  @Size(min = 1, max = 255, message = "title must be between 1 and 100 characters")
  String title,

  @Size(min = 1, max = 255, message = "details must be between 1 and 100 characters")
  String details
) {
  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public Optional<String> getDetails() {
    return Optional.ofNullable(details);
  }

}
