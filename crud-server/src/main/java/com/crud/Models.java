package com.crud;

import java.util.ArrayList;

public final class Models {
  public record Task(int id, int revision, String title, String details, String dateCreated, String dateUpdated) {}
  public record CreateTaskRequest(String title, String details) {}
  public record UpdateTaskRequest(int id, int revision, String title, String details) {}
  public record DeleteTaskRequest(int id, int revision) {}
  public record Paging(int limit, int offset, int totalCount) {}
  public record ListTasksResponse(ArrayList<Task> tasks, Paging paging) {}
}
