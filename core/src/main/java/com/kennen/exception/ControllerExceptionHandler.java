package com.kennen.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = KennenException.class)
    public ExceptionResponse kennenException(){
        return ExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RedisConnectionFailureException.class)
    public ResponseEntity<ExceptionResponse> invalidTokenException(){
        KennenException.ExceptionType redisConnectionFailureException = KennenException.ExceptionType.REDIS_CONNECTION_FAILURE_EXCEPTION;
        return new ResponseEntity<>(ExceptionResponse.builder()
                .status(redisConnectionFailureException.getStatus())
                .code(redisConnectionFailureException.getCode())
                .error(redisConnectionFailureException.getCode())
                .message(redisConnectionFailureException.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
