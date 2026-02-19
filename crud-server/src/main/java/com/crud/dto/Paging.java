package com.crud.dto;

public record Paging(
  int limit,
  int offset,
  int totalCount
) {}
