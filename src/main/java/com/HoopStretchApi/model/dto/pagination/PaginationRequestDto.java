package com.HoopStretchApi.model.dto.pagination;

import com.HoopStretchApi.util.enums.SortDirection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PaginationRequestDto {

    @NotNull
    private int page;
    @NotNull
    private int size = 10;
    @NotNull
    private String sortBy;
    @NotNull
    private SortDirection sortDirection = SortDirection.ASC;
}
