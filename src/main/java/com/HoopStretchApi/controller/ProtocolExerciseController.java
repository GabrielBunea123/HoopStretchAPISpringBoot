package com.HoopStretchApi.controller;

import com.HoopStretchApi.mapper.ExerciseMapper;
import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseResponseDto;
import com.HoopStretchApi.service.ProtocolExerciseService;
import com.HoopStretchApi.util.enums.ExerciseType;
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

import java.util.List;

@RestController
@RequestMapping("/protocol-exercises")
@AllArgsConstructor
@Tag(name = "ProtocolExercises")
public class ProtocolExerciseController {

    private final ProtocolExerciseService protocolExerciseService;
    private final ExerciseMapper exerciseMapper;

    @PostMapping("/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Protocol exercise created",
                    content = @Content(schema = @Schema(implementation = ProtocolExerciseResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolExerciseResponseDto> createProtocolExercise(@Valid @RequestBody final ProtocolExerciseRequestDto protocolExerciseRequestDto){
        final ProtocolExerciseResponseDto protocolExerciseResponseDto = protocolExerciseService.createProtocolExercise(protocolExerciseRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolExerciseResponseDto);
    }

    @GetMapping("/{protocolId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<List<ProtocolExerciseResponseDto>> getProtocolExercises(
            @PathVariable Long protocolId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String muscleGroup,
            @RequestParam(required = false) String equipmentItem,
            @RequestParam(required = false) ExerciseType type){
        final ExerciseFilterDto exerciseFilterDto = exerciseMapper.toExerciseFilterDto(
                name,
                muscleGroup,
                equipmentItem,
                type);
        return ResponseEntity.ok(protocolExerciseService.getProtocolExercises(protocolId, exerciseFilterDto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Protocol exercise deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Protocol exercise not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable final Long id) {
        protocolExerciseService.deleteProtocolExercise(id);
        return ResponseEntity.noContent().build();
    }
}
