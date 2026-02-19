package com.crud.dto;

public record Task(
  int id,
  int revision,
  String title,
  String details,
  String dateCreated,
  String dateUpdated
) {}
