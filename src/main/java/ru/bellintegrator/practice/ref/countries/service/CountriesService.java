package ru.bellintegrator.practice.ref.countries.service;

import ru.bellintegrator.practice.ref.countries.view.CountryView;

import java.util.List;

public interface CountriesService {

    /**
     * Возвращает список стран и их кодов.
     *
     * @return список стран и их кодов
     */
    List<CountryView> list();
}
