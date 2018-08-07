package ru.bellintegrator.practice.exception;

/**
 * Генерируется в случае отсутствия искомой записи в базе данных
 */
public class RecordNotFoundException extends RuntimeException {

    /**
     * Конструктор
     *
     * @param message сообщение с описанием исключительной ситуации
     */
    public RecordNotFoundException(String message){
        super(message);
    }
}
