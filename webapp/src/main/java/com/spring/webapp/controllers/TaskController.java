package com.spring.webapp.controllers;

import com.spring.webapp.dto.DeleteRequest;
import com.spring.webapp.dto.TaskRequest;
import com.spring.webapp.dto.UpdateRequest;
import com.spring.webapp.model.Task;
import com.spring.webapp.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/task")
public class TaskController {
  private final TaskRepository taskRepository;
  private final EntityManager entityManager;

  public TaskController(TaskRepository taskRepository, EntityManager entityManager) {
    this.taskRepository = taskRepository;
    this.entityManager = entityManager;
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

  @DeleteMapping
  public void deleteTask(
    @Valid
    @RequestBody
    DeleteRequest req
  ) {
    taskRepository.deleteById(req.id());
  }

  @GetMapping("/page")
  public Page<Task> getTasks(
    @Min(value = 0, message = "page must be greater than or equal to 0")
    @Max(Integer.MAX_VALUE)
    @RequestParam(defaultValue = "0")
    int page,

    @Min(value = 1, message = "size must be greater than 0")
    @Max(value = 1000, message = "size must be less than or equal to 1000")
    @RequestParam(defaultValue = "100")
    int size
  ) {
    return taskRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTask(
    @Min(value = 1, message = "id must be greater than 0")
    @Max(Long.MAX_VALUE)
    @PathVariable
    long id
  ) {
    return taskRepository.findById(id)
      .<ResponseEntity<?>>map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(404).body("Task not found"));
  }

  @PatchMapping
  public ResponseEntity<?> updateTask(
    @Valid
    @RequestBody
    UpdateRequest req
  ) {
    var entity = taskRepository.findById(req.id());

    if (entity.isPresent()) {
      var task = entity.get();

      req.getTitle().ifPresent(task::setTitle);
      req.getDetails().ifPresent(task::setDetails);
      task.setRevision(req.revision());
      entityManager.detach(task);

      return ResponseEntity.ok(taskRepository.save(task));
    } else  {
      return ResponseEntity.status(404).body("Task not found");
    }
  }
}
