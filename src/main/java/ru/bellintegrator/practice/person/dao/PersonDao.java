package ru.bellintegrator.practice.person.dao;

import ru.bellintegrator.practice.person.model.Person;

import java.util.List;

/**
 * DAO для работы с Person
 */
public interface PersonDao {
    /**
     * Получить все объекты Person
     *
     * @return
     */
    List<Person> all();

    /**
     * Получить Person по идентификатору
     *
     * @param id
     * @return
     */
    Person loadById(Long id);

    /**
     * Получить Person по имени
     *
     * @param name
     * @return
     */
    Person loadByName(String name);

    /**
     * Сохранить Person
     *
     * @param person
     */
    void save(Person person);
}
