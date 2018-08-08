package ru.bellintegrator.practice.ref.docs.dao;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.ref.docs.model.Doc;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class DocsDaoImpl implements DocsDao {

    private final EntityManager em;

    /**
     * Конструктор
     *
     * @param em контекст
     */
    @Autowired
    public DocsDaoImpl(EntityManager em){
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Doc> list() {
        TypedQuery<Doc> query = em.createQuery("SELECT d FROM Doc d", Doc.class);
        List<Doc> list = query.getResultList();
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Doc getByCode(Integer code){
        TypedQuery<Doc> query = em.createQuery("SELECT d FROM Doc d WHERE d.Code = :code", Doc.class);
        query.setParameter("code", code);
        Doc doc;
        try {
            doc = query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        return doc;
    }
}
