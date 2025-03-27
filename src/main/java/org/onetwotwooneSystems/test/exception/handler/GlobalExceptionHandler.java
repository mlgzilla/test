package org.onetwotwooneSystems.test.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.onetwotwooneSystems.test.exception.ResourceNotFoundException;
import org.onetwotwooneSystems.test.exception.handler.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorModel> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
