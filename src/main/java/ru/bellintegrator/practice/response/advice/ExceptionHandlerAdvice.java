package ru.bellintegrator.practice.response.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;
import ru.bellintegrator.practice.response.view.ErrorResponseView;

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
    public ErrorResponseView exceptionHandler(RuntimeException e){
        ErrorResponseView view = new ErrorResponseView();
        view.error = e.getMessage();
        return view;
    }

    @ExceptionHandler({Exception.class})
    public ErrorResponseView unpredictableExceptionHandler(Exception e){
        ErrorResponseView view = new ErrorResponseView();
        view.error = "An unexpected error occurred. Please contact the administrator.";
        return view;
    }
}
