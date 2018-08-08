package ru.bellintegrator.practice.ref.countries.dao;

import ru.bellintegrator.practice.ref.countries.model.Country;

import java.util.List;

/**
 * DAO кодов стран
 */
public interface CountriesDao {

    /**
     * Возвращает список стран и их кодов
     *
     * @return список стран и их кодов
     */
    List<Country> list();
}
