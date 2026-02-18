package com.spring.webapp.controllers;

import com.spring.webapp.dto.TaskRequest;
import com.spring.webapp.model.Task;
import com.spring.webapp.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
  private final TaskRepository taskRepository;

  public TaskController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PutMapping
  public Task saveTask(
    @Valid
    @RequestBody
    TaskRequest req
  ) {
    var entity = new Task()
      .setTitle(req.title())
      .setDetails(req.details());

    return taskRepository.save(entity);
  }
}
