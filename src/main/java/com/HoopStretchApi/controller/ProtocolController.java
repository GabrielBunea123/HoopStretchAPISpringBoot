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

    @PostMapping("/ai-generation")
    @Operation(
            summary = "Custom AI generated protocol",
            description = "Generate a protocol, tailored to the user, using AI"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Protocol generated successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto>generateProtocol(
            @Valid @RequestBody final ProtocolGenerationRequestDto protocolGenerationRequestDto,
            @AuthenticationPrincipal final UserDetails userDetails
    ){
        final ProtocolResponseDto protocolResponseDto = protocolGenerationService.generateProtocol(userDetails.getUsername(), protocolGenerationRequestDto);
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

    @PostMapping("/me")
    @Operation(
            summary = "Create user protocol",
            description = "Create a protocol, only accessible by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User protocol created from scratch was a success",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> createUserProtocol(
            @Valid @RequestBody final ProtocolRequestDto protocolRequestDto,
            @AuthenticationPrincipal final UserDetails userDetails
    ){
        final ProtocolResponseDto protocolResponseDto = protocolService.createUserProtocol(protocolRequestDto, userDetails.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolResponseDto);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get user protocols",
            description = "Get a list of paginated and filtred user protocols"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched user protocols successfully",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User protocols not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<PaginationResponseDto<ProtocolResponseDto>>getUserProtocols(
            @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int size,
            @RequestParam(required = false) final String sortBy,
            @RequestParam(required = false, defaultValue = SortDirection.DEFAULT_SORT_DIRECTION) final SortDirection sortDirection,
            @RequestParam(required = false, defaultValue = "") final String name,
            @RequestParam(required = false, defaultValue = ProtocolVisibility.DEFAULT_PROTOCOL_VISIBILITY) final ProtocolVisibility visibility,
            @AuthenticationPrincipal final UserDetails userDetails){
        final PaginationRequestDto paginationRequestDto = paginationMapper.toPaginationRequestDto(
                page,
                size,
                sortBy,
                String.valueOf(sortDirection)
        );
        final ProtocolFilterDto protocolFilterDto = protocolMapper.toProtocolFilterDto(name, visibility);
        final PaginationResponseDto<ProtocolResponseDto> protocols = protocolService.getUserProtocols(userDetails.getUsername(), paginationRequestDto,protocolFilterDto);
        return ResponseEntity.ok(protocols);
    }


    @PostMapping("/me/{protocolId}")
    @Operation(
            summary = "Create user protocol from generated protocol"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User protocol created successfully from already generated protocol",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto>createUserProtocolFromGeneratedProtocol(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long protocolId){
        // TODO: implement it
        return null;
    }

    @GetMapping("/me/{protocolId}")
    @Operation(
            summary = "Get user protocol by id",
            description = "Given the id of the protocol and the user, fetch the protocol"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User protocol found",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User protocol not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto>getUserProtocolById(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long protocolId){

        return ResponseEntity.ok(protocolService.getUserProtocolById(userDetails.getUsername(), protocolId));
    }

}
