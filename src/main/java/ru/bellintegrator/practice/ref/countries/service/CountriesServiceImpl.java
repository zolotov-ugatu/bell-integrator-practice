package ru.bellintegrator.practice.ref.countries.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.ref.countries.view.CountryView;

import java.util.List;

/**
 * Сервис, предоставляющий методы для получения справочной информации о странах.
 */
@Service
public class CountriesServiceImpl implements CountriesService{

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryView> list() {
        return null;
    }
}
