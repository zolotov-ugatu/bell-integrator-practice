package ru.bellintegrator.practice.office.dao;

import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.office.model.Office;

import java.util.List;


/**
 * {@inheritDoc}
 */
@Repository
public class OfficeDaoImpl implements OfficeDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Office> list(Office filter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office getById(Long officeId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Office office) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Office office) {

    }
}
