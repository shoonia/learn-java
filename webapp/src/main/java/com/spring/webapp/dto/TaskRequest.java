package com.spring.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
  @NotBlank(message = "title cannot be empty")
  @Size(min = 1, max = 255, message = "title must be between 1 and 100 characters")
  String title,
  @NotBlank(message = "details cannot be empty")
  @Size(min = 1, max = 255, message = "details must be between 1 and 100 characters")
  String details
) {

}
