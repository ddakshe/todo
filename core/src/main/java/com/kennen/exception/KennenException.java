package com.kennen.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class KennenException extends RuntimeException {

    private static final long serialVersionUID = 6186988981814896708L;

    private static final int CUSTOM_STATUS= 999;

    @Getter
    @AllArgsConstructor
    public enum ExceptionType {
        REDIS_CONNECTION_FAILURE_EXCEPTION("REDIS_CONNECTION_FAILURE_EXCEPTION",CUSTOM_STATUS, "Redis 연결 실패"),
        TODO_UPDATE_EXCEPTION("TODO_UPDATE_EXCEPTION",CUSTOM_STATUS, "Task 수정 실패");
        private String code;
        private int status;
        private String message;
    }

}
