package com.spring.webapp.repository;

import com.spring.webapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  // Basic CRUD methods are built-in
}
