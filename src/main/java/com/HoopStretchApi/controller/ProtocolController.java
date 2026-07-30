package com.HoopStretchApi.controller;

import com.HoopStretchApi.mapper.PaginationMapper;
import com.HoopStretchApi.mapper.ProtocolMapper;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolGenerationRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.dto.protocol.UpdateUserProtocolRequestDto;
import com.HoopStretchApi.service.ProtocolGenerationService;
import com.HoopStretchApi.service.ProtocolService;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.HoopStretchApi.util.Constants.DEFAULT_PAGE;
import static com.HoopStretchApi.util.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/protocols")
@AllArgsConstructor
@Tag(name = "Protocols")
public class ProtocolController {

    private final ProtocolService protocolService;
    private final ProtocolGenerationService protocolGenerationService;
    private final PaginationMapper paginationMapper;
    private final ProtocolMapper protocolMapper;

    @PostMapping("/me/generate")
    @Operation(
            summary = "Generate AI protocol",
            description = "Generate a protocol tailored to the user using AI"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Protocol generated successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> generateProtocol(
            @Valid @RequestBody final ProtocolGenerationRequestDto protocolGenerationRequestDto,
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        final ProtocolResponseDto protocolResponseDto = protocolGenerationService.generateProtocol(userDetails.getUsername(), protocolGenerationRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolResponseDto);
    }

    @PostMapping("/me")
    @Operation(
            summary = "Create user protocol",
            description = "Create a protocol manually, only accessible by the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User protocol created successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> createUserProtocol(
            @Valid @RequestBody final ProtocolRequestDto protocolRequestDto,
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        final ProtocolResponseDto protocolResponseDto = protocolService.createUserProtocol(protocolRequestDto, userDetails.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolResponseDto);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get user protocols",
            description = "Get a paginated and filtered list of the authenticated user's protocols"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched user protocols successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User protocols not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<PaginationResponseDto<ProtocolResponseDto>> getUserProtocols(
            @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int size,
            @RequestParam(required = false) final String sortBy,
            @RequestParam(required = false, defaultValue = SortDirection.DEFAULT_SORT_DIRECTION) final SortDirection sortDirection,
            @RequestParam(required = false, defaultValue = "") final String name,
            @RequestParam(required = false, defaultValue = ProtocolVisibility.DEFAULT_PROTOCOL_VISIBILITY) final ProtocolVisibility visibility,
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        final PaginationRequestDto paginationRequestDto = paginationMapper.toPaginationRequestDto(
                page,
                size,
                sortBy,
                String.valueOf(sortDirection)
        );
        final ProtocolFilterDto protocolFilterDto = protocolMapper.toProtocolFilterDto(name, visibility);
        final PaginationResponseDto<ProtocolResponseDto> protocols = protocolService.getUserProtocols(
                userDetails.getUsername(),
                paginationRequestDto,
                protocolFilterDto
        );
        return ResponseEntity.ok(protocols);
    }

    @GetMapping("/me/{protocolId}")
    @Operation(
            summary = "Get user protocol by id",
            description = "Fetch a specific protocol belonging to the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User protocol found",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User protocol not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> getUserProtocolById(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long protocolId
    ) {
        return ResponseEntity.ok(protocolService.getUserProtocolById(userDetails.getUsername(), protocolId));
    }

    @PostMapping("/{protocolId}/copy")
    @Operation(
            summary = "Copy protocol",
            description = "Copy an existing protocol into the authenticated user's own protocols"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Protocol copied successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Protocol not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> copyProtocol(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long protocolId
    ) {
        final ProtocolResponseDto protocol = protocolService.copyProtocolIntoUserProtocol(userDetails.getUsername(), protocolId);
        return ResponseEntity.ok(protocol);
    }

    @PutMapping("/me/{protocolId}")
    @Operation(
            summary = "Update user protocol",
            description = "Update a protocol belonging to the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Protocol updated successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Protocol not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> updateUserProtocol(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long protocolId,
            @Valid @RequestBody final UpdateUserProtocolRequestDto updateUserProtocolRequestDto
    ) {
        return ResponseEntity.ok(protocolService.updateUserProtocol(userDetails.getUsername(), protocolId, updateUserProtocolRequestDto));
    }

    @DeleteMapping("/me/{protocolId}")
    @Operation(
            summary = "Delete user protocol",
            description = "Delete a protocol belonging to the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Protocol deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Protocol not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)
    })
    public ResponseEntity<Void> deleteUserProtocol(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long protocolId
    ) {
        // TODO: implement
        return null;
    }

    @PostMapping("/public")
    @Operation(
            summary = "Create public protocol",
            description = "Create a public protocol, available for all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Public protocol created successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> createPublicProtocol(
            @Valid @RequestBody final ProtocolRequestDto protocolRequestDto
    ){
        final ProtocolResponseDto protocolResponseDto = protocolService.createPublicProtocol(protocolRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolResponseDto);
    }

    @PostMapping("/mobility-test")
    @Operation(
            summary = "Create mobility test protocol",
            description = "Create a general mobility test used for assessing user mobility"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mobility test created successfully",
                    content = @Content(schema = @Schema(implementation = MobilityAssessmentResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> createMobilityTestProtocol(
            @Valid @RequestBody final List<Long> exerciseIds
    ) {
        final ProtocolResponseDto protocolResponseDto = protocolService.createMobilityTestProtocol(exerciseIds);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolResponseDto);
    }

}
