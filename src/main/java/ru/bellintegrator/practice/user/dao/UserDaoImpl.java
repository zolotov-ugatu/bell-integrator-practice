package ru.bellintegrator.practice.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.user.model.User;

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
public class UserDaoImpl implements UserDao {

    private final EntityManager em;

    /**
     * Конструктор
     *
     * @param em контекст
     */
    @Autowired
    public UserDaoImpl(EntityManager em){
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> list(User user) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate predicate = criteriaBuilder.equal(userRoot.get("office").get("id"), user.getOffice().getId());
        if (user.getFirstName() != null){
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(userRoot.get("firstName"), "%" + user.getFirstName() + "%"));
        }
        if (user.getLastName() != null){
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(userRoot.get("lastName"), "%" + user.getLastName() + "%"));
        }
        if (user.getMiddleName() != null){
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(userRoot.get("middleName"), "%" + user.getMiddleName() + "%"));
        }
        if (user.getPosition() != null){
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(userRoot.get("position"), "%" + user.getPosition() + "%"));
        }
        if (user.getDoc().getCode() != null){
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(userRoot.get("doc").get("code"), user.getDoc().getCode()));
        }
        if (user.getCountry().getCode() != null){
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(userRoot.get("country").get("code"), user.getCountry().getCode()));
        }
        criteriaQuery.select(userRoot).where(predicate);
        TypedQuery<User> query = em.createQuery(criteriaQuery);
        List<User> filteredList = query.getResultList();
        return filteredList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(Long userId) {
        return em.find(User.class, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User freshUser) {
        User user = getById(freshUser.getId());
        user.setFirstName(freshUser.getFirstName());
        user.setLastName(freshUser.getLastName());
        user.setMiddleName(freshUser.getMiddleName());
        user.setPosition(freshUser.getPosition());
        user.setPhone(freshUser.getPhone());
        user.setDoc(freshUser.getDoc());
        user.setDocNumber(freshUser.getDocNumber());
        user.setDocDate(freshUser.getDocDate());
        user.setCountry(freshUser.getCountry());
        user.setIdentified(freshUser.getIdentified());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User user) {
        em.persist(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id){
        em.remove(getById(id));
    }
}
