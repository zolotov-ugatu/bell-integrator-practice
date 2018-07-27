package ru.bellintegrator.practice.ref.docs.service;

import ru.bellintegrator.practice.ref.docs.view.DocView;

import java.util.List;

public interface DocsService {

    /**
     * Возвращает список документов и их кодов.
     *
     * @return список документов и их кодов
     */
    List<DocView> list();
}
