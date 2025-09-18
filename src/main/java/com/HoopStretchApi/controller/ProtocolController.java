package com.HoopStretchApi.controller;

import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.service.ProtocolService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protocols")
@AllArgsConstructor
@Tag(name = "Protocols")
public class ProtocolController {

    private final ProtocolService protocolService;

    @PostMapping("/")
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
}
