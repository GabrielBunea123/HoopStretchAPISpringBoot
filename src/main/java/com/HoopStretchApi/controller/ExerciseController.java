package com.HoopStretchApi.controller;

import com.HoopStretchApi.mapper.ExerciseMapper;
import com.HoopStretchApi.mapper.PaginationMapper;
import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.user.UserResponseDto;
import com.HoopStretchApi.service.ExerciseService;
import com.HoopStretchApi.util.enums.ExerciseType;
import com.HoopStretchApi.util.enums.SortDirection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
@AllArgsConstructor
@Tag(name = "Exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final PaginationMapper paginationMapper;
    private final ExerciseMapper exerciseMapper;

    @GetMapping("/")
    @Operation(
            summary = "Get exercises",
            description = "Returns a paginated and sorted list of exercises"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<PaginationResponseDto<ExerciseResponseDto>> getExercises(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size,
            @RequestParam(required = false) final String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") final SortDirection sortDirection,
            @RequestParam(required = false, defaultValue = "") final String name,
            @RequestParam(required = false) final String muscleGroup,
            @RequestParam(required = false) final String equipmentItem,
            @RequestParam(required = false) final ExerciseType type) {
        final PaginationRequestDto paginationRequestDto = paginationMapper.toPaginationRequestDto(
                page,
                size,
                sortBy,
                String.valueOf(sortDirection));
        final ExerciseFilterDto exerciseFilterDto = exerciseMapper.toExerciseFilterDto(
                name,
                muscleGroup,
                equipmentItem,
                type);
        return ResponseEntity.ok(exerciseService.getExercises(paginationRequestDto, exerciseFilterDto));
    }

}
