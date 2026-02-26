package org.acme.dto;

public record Paging(
  int page,
  int size,
  long totalCount,
  int pageCount
) {

}
