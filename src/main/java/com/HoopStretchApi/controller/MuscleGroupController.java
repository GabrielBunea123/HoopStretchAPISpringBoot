package com.HoopStretchApi.controller;

import com.HoopStretchApi.model.dto.muscleGroup.MuscleGroupResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.service.MuscleGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/muscle-groups")
@AllArgsConstructor
@Tag(name = "MuscleGroups")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    @GetMapping("/")
    @Operation(
            summary = "Get all muscle groups",
            description = "Returns a list of all muscle groups"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all muscle groups",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDto.class)))
    })
    public ResponseEntity<List<MuscleGroupResponseDto>> getAllMuscleGroups() {
        return ResponseEntity.ok(muscleGroupService.getAllMuscleGroups());
    }
}
