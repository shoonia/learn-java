package com.spring.webapp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String title;

  @Column(nullable = false, length = 255)
  private String details;

  @Column(columnDefinition = "INTEGER DEFAULT 1")
  private Integer revision;

  @Column(
    name = "date_created",
    nullable = false,
    updatable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
  )
  @CreationTimestamp
  private Date dateCreated;

  @Column(
    name = "date_updated",
    nullable = false,
    updatable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
  )
  @CreationTimestamp
  private Date dateUpdated;

  public Task setTitle(String title) {
    this.title = title;
    return this;
  }

  public Task setDetails(String details) {
    this.details = details;
    return this;
  }
}
