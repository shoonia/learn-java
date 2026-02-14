package com.crud;

public final class Models {
  public record Task(int id, String title, String details) {}
  public record CreateTaskRequest(String title, String details) {}
  public record UpdateTaskRequest(int id, String title, String details) {}
  public record DeleteTaskRequest(int id) {}
}
