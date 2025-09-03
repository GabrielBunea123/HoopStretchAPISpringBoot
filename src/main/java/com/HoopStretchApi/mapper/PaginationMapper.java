package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaginationMapper {

    @Mapping(target = "page", source = "page")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "sortBy", source = "sortBy")
    @Mapping(target = "sortDirection", source = "sortDirection")
    PaginationRequestDto toPaginationRequestDto(final int page, final int size, final String sortBy, final String sortDirection);

}