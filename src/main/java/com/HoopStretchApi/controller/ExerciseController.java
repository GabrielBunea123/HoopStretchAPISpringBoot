package com.HoopStretchApi.controller;

import com.HoopStretchApi.mapper.ExerciseMapper;
import com.HoopStretchApi.mapper.PaginationMapper;
import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseRequestDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
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

    @Operation(summary = "Get exercise by id",
            description = "Gets an existing exercise by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise fetched successfully",
                    content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDto> getExerciseById(
            @PathVariable final Long id) {
        final ExerciseResponseDto exerciseResponseDto = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(exerciseResponseDto);
    }

    @PostMapping("/")
    @Operation(
            summary = "Add exercise",
            description = "Creates a new exercises and returns it"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User found",
                    content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ExerciseResponseDto> createExercise(@Valid @RequestBody final ExerciseRequestDto exerciseRequestDto){
        final ExerciseResponseDto exerciseResponseDto = exerciseService.createExercise(exerciseRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exerciseResponseDto);
    }

    @Operation(summary = "Update an exercise",
            description = "Updates an existing exercise by its ID. Requires full object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully",
                    content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDto> updateExercise(
            @PathVariable final Long id,
            @RequestBody ExerciseRequestDto exerciseRequestDto) {
        final ExerciseResponseDto exerciseResponseDto = exerciseService.updateExercise(id, exerciseRequestDto);
        return ResponseEntity.ok(exerciseResponseDto);
    }

    @Operation(summary = "Delete an exercise",
            description = "Deletes an existing exercise by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exercise deleted successfully",
                    content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable final Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

}
