package com.spring.webapp.model;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Task {

  protected Task() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ReadOnlyProperty
  private Long id;

  @Builder.Default
  @Column(columnDefinition = "INTEGER DEFAULT 1")
  private Integer revision = 1;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String details;

  @CreationTimestamp
  @Column(
    name = "date_created",
    nullable = false,
    updatable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
  )
  private Date dateCreated;

  @UpdateTimestamp
  @Column(
    name = "date_updated",
    nullable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
  )
  private Date dateUpdated;

}
