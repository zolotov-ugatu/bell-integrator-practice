package ru.bellintegrator.practice.user.dao;

import ru.bellintegrator.practice.user.model.User;

import java.util.List;

/**
 * DAO для работы с пользователями
 */
public interface UserDao {

    /**
     * Возвращает отфильтрованный список пользователей
     *
     * @param user фильтр
     * @return отфильтрованный список пользователей
     */
    List<User> list(User user);

    /**
     * Возвращает пользователя с указанным идентификатором
     *
     * @param userId идентификатор
     * @return пользователь с указанным идентификатором
     */
    User getById(Long userId);

    /**
     * Обновляет данные о пользователе
     *
     * @param user объект с обновленными данными
     */
    void update(User user);

    /**
     * Сохраняет нового пользователя
     *
     * @param user объект с данными о новом пользователе
     */
    void save(User user);
}
