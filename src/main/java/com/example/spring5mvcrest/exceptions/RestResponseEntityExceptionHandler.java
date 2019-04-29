package com.example.spring5mvcrest.exceptions;

import com.example.spring5mvcrest.services.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

/*    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){
            return new ResponseEntity<Object>("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }*/

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), request.getDescription(false));

        return   new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //  ExceptionResponse exceptionResponse =  new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
        ExceptionResponse exceptionResponse =  new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());

        return   new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
