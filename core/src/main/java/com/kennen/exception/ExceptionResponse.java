package com.kennen.exception;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = -3422560902405841014L;

    private int status;
    private String code;
    private String error;
    private String message;
}

