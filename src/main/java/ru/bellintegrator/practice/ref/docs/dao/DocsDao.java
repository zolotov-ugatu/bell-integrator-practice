package ru.bellintegrator.practice.ref.docs.dao;

import ru.bellintegrator.practice.ref.docs.model.Doc;

import java.util.List;

/**
 * DAO документов
 */
public interface DocsDao {

    /**
     * Возвращает список документов, удостоверяющих личность и их кодов
     *
     * @return список, содержащий коды и наименования документов
     */
    List<Doc> list();

    /**
     * Возвращает документ с указанным кодом
     *
     * @return код документа
     */
    Doc getByCode(Integer code);
}
