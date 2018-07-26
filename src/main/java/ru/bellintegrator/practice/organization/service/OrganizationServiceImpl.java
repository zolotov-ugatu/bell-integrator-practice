package ru.bellintegrator.practice.organization.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.organization.view.OrganizationListFilter;
import ru.bellintegrator.practice.organization.view.OrganizationToSave;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrganizationView> list(OrganizationListFilter filter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationView getById(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(OrganizationView view) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OrganizationToSave orgToSave) {

    }
}
