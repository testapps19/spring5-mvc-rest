package com.example.spring5mvcrest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

}
