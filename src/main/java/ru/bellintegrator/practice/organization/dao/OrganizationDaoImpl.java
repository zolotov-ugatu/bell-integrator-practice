package ru.bellintegrator.practice.organization.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.organization.model.Organization;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    private final EntityManager em;

    /**
     * Конструктор
     *
     * @param em контекст
     */
    @Autowired
    public OrganizationDaoImpl(EntityManager em){
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> list(Organization filter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
        Root<Organization> organizationRoot = criteriaQuery.from(Organization.class);
        Predicate predicate = criteriaBuilder.like(organizationRoot.get("name"), "%" + filter.getName() + "%");
        if (filter.getInn() != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(organizationRoot.get("inn"), filter.getInn()));
        }
        if (filter.getActive() != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(organizationRoot.get("isActive"), filter.getActive()));
        }
        criteriaQuery.select(organizationRoot).where(predicate);
        TypedQuery<Organization> query = em.createQuery(criteriaQuery);
        List<Organization> filteredList = query.getResultList();
        return filteredList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization getById(Long id) {
        return em.find(Organization.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Organization freshOrganization) {
        Organization organization = getById(freshOrganization.getId());
        organization.setName(freshOrganization.getName());
        organization.setFullName(freshOrganization.getFullName());
        organization.setInn(freshOrganization.getInn());
        organization.setKpp(freshOrganization.getKpp());
        organization.setAddress(freshOrganization.getAddress());
        organization.setPhone(freshOrganization.getPhone());
        organization.setActive(freshOrganization.getActive());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Organization organization) {
        em.persist(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id){
        em.remove(getById(id));
    }
}
