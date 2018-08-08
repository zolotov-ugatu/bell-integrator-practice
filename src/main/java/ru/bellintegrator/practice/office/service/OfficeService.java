package ru.bellintegrator.practice.office.service;

import ru.bellintegrator.practice.office.view.OfficeListFilter;
import ru.bellintegrator.practice.office.view.OfficeToSave;
import ru.bellintegrator.practice.office.view.OfficeView;

import java.util.List;

/**
 * Интерфейс сервиса для работы с офисами
 */
public interface OfficeService {

    /**
     * Возвращает отфильтрованный список офисов.
     *
     * @param filter фильтр для списка
     * @return отфильтрованный список
     */
    List<OfficeView> list(OfficeListFilter filter);


    /**
     * Возвращает офис с указанным идентификатором.
     *
     * @param officeId идентификатор офиса
     * @return офис с указанным идентификатором
     */
    OfficeView getById(Long officeId);

    /**
     * Обновляет сведения об офисе
     *
     * @param view объект, содержащий новые сведения об офисе.
     */
    void update(OfficeView view);

    /**
     * Сохраняет сведения о новом офисе.
     *
     * @param officeToSave объект, содержащий сведения о новом офисе
     */
    void save(OfficeToSave officeToSave);

    /**
     * Удаляет офис с указанным идентификатором
     *
     * @param id идентификатор офиса для удаления
     */
    void remove(Long id);
}
