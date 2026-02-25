package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class Task extends PanacheEntity {
  @Version
  public Integer revision = 1;

  @NotBlank(message="Title is required")
  @Column(nullable = false)
  public String title;

  @Column
  public String details;

  @CreationTimestamp
  @Column(
    name = "date_created",
    nullable = false,
    updatable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
  )
  public Date dateCreated;

  @UpdateTimestamp
  @Column(
    name = "date_updated",
    nullable = false,
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
  )
  public Date dateUpdated;
}
