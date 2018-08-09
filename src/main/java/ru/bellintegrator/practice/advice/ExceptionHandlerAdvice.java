package ru.bellintegrator.practice.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;

/**
 * Содержит обработчики исключений, формирующие соответствующий ответ
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({WrongRequestException.class, RecordNotFoundException.class})
    public ExceptionView exceptionHandler(RuntimeException e){
        ExceptionView view = new ExceptionView();
        view.error = e.getMessage();
        return view;
    }

    private class ExceptionView{
        public String error;
    }
}
