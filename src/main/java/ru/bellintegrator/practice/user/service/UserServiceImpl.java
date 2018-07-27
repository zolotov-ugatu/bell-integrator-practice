package ru.bellintegrator.practice.user.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.user.view.UserListFilter;
import ru.bellintegrator.practice.user.view.UserToSave;
import ru.bellintegrator.practice.user.view.UserView;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserView> list(UserListFilter filter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserView getById(Long userId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(UserView view) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(UserToSave userToSave) {

    }
}
