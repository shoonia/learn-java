package com.crud;

import java.util.ArrayList;

public final class Models {
  public record Task(int id, String title, String details) {}
  public record CreateTaskRequest(String title, String details) {}
  public record UpdateTaskRequest(int id, String title, String details) {}
  public record DeleteTaskRequest(int id) {}
  public record Paging(int limit, int offset, int totalCount) {}
  public record ListTasksResponse(ArrayList<Task> tasks, Paging paging) {}
}
