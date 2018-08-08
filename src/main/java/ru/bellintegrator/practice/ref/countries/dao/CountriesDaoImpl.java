package ru.bellintegrator.practice.ref.countries.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.ref.countries.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class CountriesDaoImpl implements CountriesDao {

    private final EntityManager em;

    /**
     * Конструктор
     *
     * @param em контекст
     */
    @Autowired
    public CountriesDaoImpl(EntityManager em){
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> list() {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c", Country.class);
        List<Country> list = query.getResultList();
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Country getByCode(Integer code){
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.code = :code", Country.class);
        query.setParameter("code", code);
        Country country;
        try {
            country = query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        return country;
    }
}
