package ru.bellintegrator.practice.exception;

/**
 * Выбрасывается при отсутствии обязательных полей в запросе, либо некорректном значении поля.
 */
public class WrongRequestException extends RuntimeException {

    /**
     * Конструктор
     *
     * @param message сообщение с описанием исключительной ситуации
     */
    public WrongRequestException(String message){
        super(message);
    }
}
