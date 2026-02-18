package com.spring.webapp.controllers;

import com.spring.webapp.dto.TaskRequest;
import com.spring.webapp.model.Task;
import com.spring.webapp.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    var task = Task.builder()
      .title(req.title())
      .details(req.details())
      .build();

    return taskRepository.save(task);
  }

  @GetMapping("/page")
  public Page<Task> getTasks(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "100") int size
  ) {
    return taskRepository.findAll(PageRequest.of(page, size));
  }
}
