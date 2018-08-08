package ru.bellintegrator.practice.user.service;

import ru.bellintegrator.practice.user.view.UserListFilter;
import ru.bellintegrator.practice.user.view.UserToSave;
import ru.bellintegrator.practice.user.view.UserView;

import java.util.List;

public interface UserService {

    /**
     * Возвращает отфильтрованный список пользователей.
     *
     * @param filter объект, содержащий сведения для фильтрации
     * @return отфильтрованный список пользователей
     */
    List<UserView> list(UserListFilter filter);

    /**
     * Возвращает пользователя с указанным идентификатором.
     *
     * @param userId идентификатор пользователя
     * @return пользователь с указанным идентификатором
     */
    UserView getById(Long userId);

    /**
     * Обновляет сведения о пользователе.
     *
     * @param view объект, содержащий новые данные о пользователе
     */
    void update(UserView view);

    /**
     * Сохраняет нового пользователя.
     *
     * @param userToSave объект, содержащий сведения о нововм пользователе
     */
    void save(UserToSave userToSave);

    /**
     * Удаляет пользователя с указанным идентификатором
     *
     * @param id идентификатор пользователя для удаления
     */
    void remove(Long id);
}
