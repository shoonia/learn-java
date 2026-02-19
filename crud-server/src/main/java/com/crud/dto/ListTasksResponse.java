package com.crud.dto;

import java.util.ArrayList;

public record ListTasksResponse(
  ArrayList<Task> tasks,
  Paging paging
) {}
