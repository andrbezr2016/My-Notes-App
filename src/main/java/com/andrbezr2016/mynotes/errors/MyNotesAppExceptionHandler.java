package com.andrbezr2016.mynotes.errors;

import com.andrbezr2016.mynotes.dto.ErrorDto;
import com.andrbezr2016.mynotes.dto.ValidationErrorDto;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@Slf4j
@RestControllerAdvice
public class MyNotesAppExceptionHandler {

    @ExceptionHandler(MyNotesAppException.class)
    public ResponseEntity<ErrorDto> handleMyException(MyNotesAppException exception) {
        return new ResponseEntity<>(ErrorDto.builder().message(exception.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> handleValidationException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        List<ValidationErrorDto> errorDtoList = exception.getBindingResult().getFieldErrors().stream()
                .map((fieldError) -> ValidationErrorDto.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build())
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorDtoList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationErrorDto>> handleValidationException(ConstraintViolationException exception) {
        log.warn(exception.getMessage());
        List<ValidationErrorDto> errorDtoList = exception.getConstraintViolations().stream()
                .map((fieldError) -> ValidationErrorDto.builder().field(fieldError.getPropertyPath().toString()).message(fieldError.getMessage()).build())
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorDtoList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleAllException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ErrorDto.builder().message(EXCEPTION_GENERIC).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
