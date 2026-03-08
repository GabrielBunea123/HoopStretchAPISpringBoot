package com.HoopStretchApi.controller;

import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentRequestDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentResponseDto;
import com.HoopStretchApi.service.MobilityAssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mobility-assessment")
@AllArgsConstructor
@Tag(name = "MobilityAssessment")
public class MobilityAssessmentController {

    private final MobilityAssessmentService mobilityAssessmentService;

    @PostMapping("/")
    @Operation(
            summary = "Create mobility assessment for user",
            description = "Create the mobility assessment with all the mobility areas and corresponding scores"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User mobility assessed successfully",
                content = @Content(schema = @Schema(implementation = MobilityAssessmentResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<MobilityAssessmentResponseDto> createUserMobilityAssessment(
        @AuthenticationPrincipal final UserDetails userDetails,
        @Valid @RequestBody final MobilityAssessmentRequestDto mobilityAssessmentRequestDto
    ){
        final MobilityAssessmentResponseDto assessment = mobilityAssessmentService.createMobilityAssessment(userDetails.getUsername(), mobilityAssessmentRequestDto);
        return ResponseEntity.ok().body(assessment);
    }

    @GetMapping("/latest")
    @Operation(
            summary = "Get latest mobility assessment for user",
            description = "Get the latest mobility assessment with all the mobility areas and corresponding scores"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User mobility assessment fetched successfully",
                    content = @Content(schema = @Schema(implementation = MobilityAssessmentResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<MobilityAssessmentResponseDto> getUserLatestMobilityAssessment(
            @AuthenticationPrincipal final UserDetails userDetails){
        return ResponseEntity.ok().body(mobilityAssessmentService.getLatestMobilityAssessment(userDetails.getUsername()));
    }

    @GetMapping("/main-areas/{assessmentId}")
    @Operation(
            summary = "Get mobility assessment main areas by assessment id",
            description = "Get mobility assessment with the main mobility areas and corresponding scores by assessment id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mobility assessment fetched successfully",
                    content = @Content(schema = @Schema(implementation = MobilityAssessmentResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<MobilityAssessmentResponseDto>getAssessmentMainAreasByAssessmentId(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long assessmentId){
        final MobilityAssessmentResponseDto response = mobilityAssessmentService.getAssessmentMainAreasByAssessmentId(assessmentId);
        return ResponseEntity.ok().body(response);
    }
}
