package ru.bellintegrator.practice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;

/**
 * Содержит обработчики исключений, формирующие соответствующий ответ
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * Формирует сообщение об ошибке бля пользователя
     *
     * @param e обрабатываемое исключение
     * @return view с сообщением об ошибке
     */
    @ExceptionHandler({WrongRequestException.class, RecordNotFoundException.class,
            HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
    public Object exceptionHandler(RuntimeException e){
        return new Object(){
            public String error = e.getMessage();
        };
    }
}
