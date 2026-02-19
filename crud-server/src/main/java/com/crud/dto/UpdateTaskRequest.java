package com.crud.dto;

public record UpdateTaskRequest(
  int id,
  int revision,
  String title,
  String details
) {}
