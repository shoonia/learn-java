package org.acme.dto;

import org.acme.model.Task;

import java.util.List;

public record PageResponse(
  List<Task> tasks,
  Paging paging
) {

}
