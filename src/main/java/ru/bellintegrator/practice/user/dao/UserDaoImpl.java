package ru.bellintegrator.practice.user.dao;

import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.user.model.User;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class UserDaoImpl implements UserDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> list(User user) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(Long userId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User user) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User user) {

    }
}
