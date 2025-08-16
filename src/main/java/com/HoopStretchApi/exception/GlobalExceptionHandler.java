package com.HoopStretchApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            final NotFoundException notFoundException,
            final WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                notFoundException.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(
            final ConflictException conflictException,
            final WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                conflictException.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(
            final BadRequestException exception,
            final WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            final UnauthorizedException exception,
            final WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            final ForbiddenException exception,
            final WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            final MethodArgumentNotValidException exception,
            final WebRequest request) {

        final String errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return handleBadRequestException(new BadRequestException(errors), request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            final RuntimeException exception,
            final WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                "Something went wrong. Please try again later.",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
