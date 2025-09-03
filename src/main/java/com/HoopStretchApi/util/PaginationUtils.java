package com.HoopStretchApi.util;

import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.util.enums.SortDirection;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PaginationUtils {

    public static Pageable getPageable(final PaginationRequestDto paginationRequestDto) {

        if (paginationRequestDto.getSortBy() == null || paginationRequestDto.getSortBy().trim().isEmpty()) {
            return PageRequest.of(paginationRequestDto.getPage(), paginationRequestDto.getSize());
        }

        final Sort.Direction direction =
                paginationRequestDto.getSortDirection() == SortDirection.ASC
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;
        return PageRequest.of(
                paginationRequestDto.getPage(),
                paginationRequestDto.getSize(),
                direction,
                paginationRequestDto.getSortBy());
    }
}
