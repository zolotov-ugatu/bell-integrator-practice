package ru.bellintegrator.practice.organization.service;

import ru.bellintegrator.practice.organization.view.OrganizationListFilter;
import ru.bellintegrator.practice.organization.view.OrganizationToSave;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;

/**
 * Интерфейс сервиса для работы с организациями
 */
public interface OrganizationService {

    /**
     * Возвращает отфильтрованный список организаций.
     *
     * @param filter фильтр списка организаций
     * @return отфильтрованный список организаций
     */
    List<OrganizationView> list(OrganizationListFilter filter);

    /**
     * Возвращает организацию с указанным идентификатором.
     *
     * @param id идентификатор организации
     * @return организация с указанным идентификатором
     */
    OrganizationView getById(Long id);

    /**
     * Обновляет сведения об организации.
     *
     * @param view объект с обновленными данными
     */
    void update(OrganizationView view);

    /**
     * Сохраняет сведения о новой организации.
     *
     * @param orgToSave объект, содержащий сведения о новой организации
     */
    void save(OrganizationToSave orgToSave);
}
