package com.crud;

public class Models {
  record Task(int id, String title, String details) {}
  record CreateTaskRequest(String title, String details) {}
}
