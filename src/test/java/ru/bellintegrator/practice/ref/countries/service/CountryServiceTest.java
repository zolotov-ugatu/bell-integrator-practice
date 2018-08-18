package ru.bellintegrator.practice.ref.countries.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.ref.countries.dao.CountriesDao;
import ru.bellintegrator.practice.ref.countries.model.Country;
import ru.bellintegrator.practice.ref.countries.view.CountryView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @InjectMocks
    private CountriesServiceImpl service;

    @Mock
    private CountriesDao dao;

    @Test
    public void testList(){
        Country country = new Country();
        country.setId(1);
        country.setCode(643);
        country.setName("Российская Федерация");
        List<Country> daoAnswer = new ArrayList<>();
        daoAnswer.add(country);
        when(dao.list()).thenReturn(daoAnswer);
        List<CountryView> list = service.list();
        verify(dao, times(1)).list();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(country.getCode(), list.get(0).code);
        Assert.assertEquals(country.getName(), list.get(0).name);
    }
}
