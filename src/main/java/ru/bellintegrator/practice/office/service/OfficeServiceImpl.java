package ru.bellintegrator.practice.office.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.office.view.OfficeListFilter;
import ru.bellintegrator.practice.office.view.OfficeToSave;
import ru.bellintegrator.practice.office.view.OfficeView;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfficeView> list(OfficeListFilter filter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfficeView getById(Long officeId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(OfficeView view) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OfficeToSave officeToSave) {

    }
}
