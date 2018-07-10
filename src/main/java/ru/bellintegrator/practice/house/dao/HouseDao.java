package ru.bellintegrator.practice.house.dao;

import ru.bellintegrator.practice.house.model.House;

import java.util.List;

/**
 * DAO для работы с House
 */
public interface HouseDao {
    /**
     * Получить все объекты House
     *
     * @return
     */
    List<House> all();

    /**
     * Получить House по идентификатору
     *
     * @param id
     * @return
     */
    House loadById(Long id);

    /**
     * Сохранить House
     *
     * @param house
     */
    void save(House house);
}
