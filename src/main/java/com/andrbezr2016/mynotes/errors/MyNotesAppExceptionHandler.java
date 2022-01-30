package com.andrbezr2016.mynotes.errors;

import com.andrbezr2016.mynotes.dto.ErrorDto;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyNotesAppExceptionHandler {

    @ExceptionHandler(MyNotesAppException.class)
    public ResponseEntity<ErrorDto> handleException(MyNotesAppException exception) {
        return new ResponseEntity<>(ErrorDto.builder().message(exception.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}
