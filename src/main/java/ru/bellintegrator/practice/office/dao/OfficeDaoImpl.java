package ru.bellintegrator.practice.office.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.office.model.Office;

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
public class OfficeDaoImpl implements OfficeDao {

    private final EntityManager em;

    /**
     * Конструктор
     *
     * @param em контекст
     */
    @Autowired
    public OfficeDaoImpl(EntityManager em){
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Office> list(Office filter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Office> criteriaQuery = criteriaBuilder.createQuery(Office.class);
        Root<Office> officeRoot = criteriaQuery.from(Office.class);
        Predicate predicate = criteriaBuilder.equal(officeRoot.get("organization").get("id"), filter.getOrganization().getId());
        if (filter.getName() != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(officeRoot.get("name"), filter.getName()));
        }
        if (filter.getPhone() != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(officeRoot.get("phone"), filter.getPhone()));
        }
        if (filter.getActive() != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(officeRoot.get("isActive"), filter.getActive()));
        }
        criteriaQuery.select(officeRoot).where(predicate);
        TypedQuery<Office> query = em.createQuery(criteriaQuery);
        List<Office> filteredList = query.getResultList();
        return filteredList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office getById(Long officeId) {
        Office office = em.find(Office.class, officeId);
        if (office == null){
            throw new RecordNotFoundException("Record with id = " + officeId + " was not found in Office");
        }
        return office;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Office freshOffice) {
        Office office = getById(freshOffice.getId());
        office.setName(freshOffice.getName());
        office.setAddress(freshOffice.getAddress());
        office.setPhone(freshOffice.getPhone());
        office.setActive(freshOffice.getActive());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Office office) {
        em.persist(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id){
        em.remove(getById(id));
    }
}
