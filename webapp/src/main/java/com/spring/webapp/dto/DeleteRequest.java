package com.spring.webapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record DeleteRequest(
  @Min(value = 0, message = "id must be greater than or equal to 0")
  @Max(Long.MAX_VALUE)
  long id
) {

}
