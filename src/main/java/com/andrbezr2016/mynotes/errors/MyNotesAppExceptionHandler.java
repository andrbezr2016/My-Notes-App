package com.andrbezr2016.mynotes.errors;

import com.andrbezr2016.mynotes.dto.ErrorDto;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@Slf4j
@RestControllerAdvice
public class MyNotesAppExceptionHandler {

    @ExceptionHandler(MyNotesAppException.class)
    public ResponseEntity<ErrorDto> handleMyException(MyNotesAppException exception) {
        return new ResponseEntity<>(ErrorDto.builder().message(exception.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleAllException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ErrorDto.builder().message(EXCEPTION_GENERIC).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
