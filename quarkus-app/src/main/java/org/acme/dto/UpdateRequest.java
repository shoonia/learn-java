package org.acme.dto;

import jakarta.validation.constraints.*;

import java.util.Optional;

public record UpdateRequest(
  @NotNull(message = "id must be provided")
  @Min(0)
  long id,

  @NotNull(message = "revision must be provided")
  @Min(0)
  long revision,

  @Size(min = 1, max = 255, message = "title must be between 1 and 100 characters")
  String title,

  @Size(max = 255, message = "details must be between 1 and 100 characters")
  String details
) {
  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public Optional<String> getDetails() {
    return Optional.ofNullable(details);
  }
}
