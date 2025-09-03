package com.HoopStretchApi.model.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PaginationResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;
}
