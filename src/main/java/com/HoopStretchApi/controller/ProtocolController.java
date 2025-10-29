package com.HoopStretchApi.controller;

import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.service.ProtocolService;
import com.HoopStretchApi.util.enums.SortDirection;
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

import static com.HoopStretchApi.util.Constants.DEFAULT_PAGE;
import static com.HoopStretchApi.util.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/protocols")
@AllArgsConstructor
@Tag(name = "Protocols")
public class ProtocolController {

    private final ProtocolService protocolService;

    @PostMapping("/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Protocol generated",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto>generateProtocol(){
        // TODO: Implement it
        return null;
    }

    @PostMapping("/me/{protocolId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User protocol created",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto>createUserProtocolFromGeneratedProtocol(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable final Long protocolId){
        // TODO: implement it
        return null;
    }

    @PostMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Protocol created",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto> createProtocol(@Valid @RequestBody ProtocolRequestDto protocolRequestDto){
        final ProtocolResponseDto protocolResponseDto = protocolService.createProtocol(protocolRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(protocolResponseDto);
    }

    @GetMapping("/me/{protocolId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User protocol found",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User protocol not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<ProtocolResponseDto>getUserProtocolById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable final Long protocolId){

        return ResponseEntity.ok(protocolService.getUserProtocolById(userDetails, protocolId));
    }

    @GetMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched user protocols",
                    content = @Content(schema = @Schema(implementation = ProtocolResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User protocols not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<PaginationResponseDto<ProtocolResponseDto>>getUserProtocols(
            @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int size,
            @RequestParam(required = false) final String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") final SortDirection sortDirection,
            @RequestParam(required = false, defaultValue = "") final String name,
            @AuthenticationPrincipal UserDetails userDetails){
        return null;
    }

}
