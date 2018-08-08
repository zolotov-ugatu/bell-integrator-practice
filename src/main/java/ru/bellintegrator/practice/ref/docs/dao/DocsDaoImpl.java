package ru.bellintegrator.practice.ref.docs.dao;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.ref.docs.model.Doc;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
}
