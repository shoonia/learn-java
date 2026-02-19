package com.crud.dto;

public record DeleteTaskRequest(
  int id,
  int revision
) {}
