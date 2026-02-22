package com.spring.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record CreateRequest(
  @NotBlank(message = "title cannot be empty")
  @Size(min = 1, max = 255, message = "title must be between 1 and 100 characters")
  String title,

  @Size(max = 255, message = "details must be between 1 and 100 characters")
  String details
) {
  public Optional<String> getDetails() {
    return Optional.ofNullable(details);
  }
}
