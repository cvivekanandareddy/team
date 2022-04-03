package org.privado.employee.team.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.privado.employee.team.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionalAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ErrorModel httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("action=controllerException, httpMessageNotReadableException={}", e.getMessage());
        return ErrorModel.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .errorMessage(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ErrorModel missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("action=controllerException, missingServletRequestParameterException={}", e.getMessage());
        return ErrorModel.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .errorMessage(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorModel methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("action=controllerException, methodArgumentNotValidException={}", e.getMessage());
        Set<String> messages = new HashSet<>(e.getBindingResult().getFieldErrors().size());
        messages.addAll(e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s='%s' : %s", fieldError.getField(),
                        fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList()));
        return ErrorModel.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .errorMessage(messages.toString())
                .build();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ErrorModel httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("action=controllerException, httpRequestMethodNotSupportedException={}", e.getMessage());
        return ErrorModel.builder()
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED.value())
                .errorCode(HttpStatus.METHOD_NOT_ALLOWED.name())
                .errorMessage(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ErrorModel httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error("action=controllerException, httpMediaTypeNotSupportedException={}", e.getMessage());
        return ErrorModel.builder()
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .errorCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name())
                .errorMessage(e.getMessage())
                .build();
    }
}
