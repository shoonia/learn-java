package org.acme.dto;

public record Paging(
  int page,
  int limit,
  long totalCount,
  int pageCount
) {

}
