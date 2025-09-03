package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaginationMapper {

    PaginationRequestDto toPaginationRequestDto(final int page, final int size, final String sortBy, final String sortDirection);
}