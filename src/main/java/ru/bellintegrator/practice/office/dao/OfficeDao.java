package ru.bellintegrator.practice.office.dao;

import ru.bellintegrator.practice.office.model.Office;

import java.util.List;

/**
 * DAO-слой для работы с офисами
 */
public interface OfficeDao {

    /**
     * Возвращает отфильтрованный список офисов
     *
     * @param filter фильтр для списка
     * @return отфильтрованный список
     */
    List<Office> list(Office filter);

    /**
     * Возвращает офис с указанным идентификатором
     *
     * @param officeId идентификатор
     * @return офис с указанным идентификатором
     */
    Office getById(Long officeId);

    /**
     * Обновляет сведения об офисе
     *
     * @param office объект с новыми сведениями об офисе
     */
    void update(Office office);

    /**
     * Сохраняет новый офис
     *
     * @param office объект со сведениями о новом офисе
     */
    void save(Office office);

    /**
     * Удаляет офис с указанным идентификатором
     *
     * @param id идентификатор офиса для удаления
     */
    void remove(Long id);
}
