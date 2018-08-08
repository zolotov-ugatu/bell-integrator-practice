package ru.bellintegrator.practice.ref.countries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.ref.countries.dao.CountriesDao;
import ru.bellintegrator.practice.ref.countries.model.Country;
import ru.bellintegrator.practice.ref.countries.view.CountryView;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервис, предоставляющий методы для получения справочной информации о странах.
 */
@Service
public class CountriesServiceImpl implements CountriesService{

    private final CountriesDao dao;

    /**
     * Конструктор
     *
     * @param dao DAO
     */
    @Autowired
    public CountriesServiceImpl(CountriesDao dao){
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<CountryView> list() {
        List<Country> list = dao.list();
        return list.stream().map(mapCountry()).collect(Collectors.toList());
    }

    private Function<Country, CountryView> mapCountry(){
        return c -> {
            CountryView view = new CountryView();
            view.code = c.getCode();
            view.name = c.getName();
            return view;
        };
    }
}
