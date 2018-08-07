package ru.bellintegrator.practice.organization.dao;

import ru.bellintegrator.practice.organization.model.Organization;

import java.util.List;

/**
 * DAO-слой для работы с организациями
 */
public interface OrganizationDao {

    /**
     * Возвращает отфильтрованный список организаций
     *
     * @param filter объект с данными фильтрации
     * @return отфильтрованный список организаций
     */
    List<Organization> list(Organization filter);

    /**
     * Возвращает организацию с указанным идентификатором
     *
     * @param id идентификатор
     * @return организация с указанным идентификатором
     */
    Organization getById(Long id);

    /**
     * Обновляет сведения об организации
     *
     * @param organization объект с новыми сведениями об организации
     */
    void update(Organization organization);

    /**
     * Сохраняет новую организацию
     *
     * @param organization объект со сведениями о новой организации
     */
    void save(Organization organization);
}
