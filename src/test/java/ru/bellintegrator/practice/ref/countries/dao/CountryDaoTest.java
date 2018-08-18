package ru.bellintegrator.practice.ref.countries.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.ref.countries.model.Country;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
@Sql(statements = {
        "DELETE FROM User;",
        "DELETE FROM Country;",
        "INSERT INTO Country (id, code, name) VALUES " +
                "(1, 643, 'Российская Федерация')," +
                "(2, 498, 'Республика Молдова');"
})
public class CountryDaoTest {

    @Autowired
    private CountriesDao dao;

    @Test
    public void testList(){
        List<Country> list = dao.list();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(643, list.get(0).getCode().intValue());
        Assert.assertEquals("Российская Федерация", list.get(0).getName());
        Assert.assertEquals(498, list.get(1).getCode().intValue());
        Assert.assertEquals("Республика Молдова", list.get(1).getName());
    }

    @Test
    public void testGetByCode(){
        Country country = dao.getByCode(643);
        Assert.assertNotNull(country);
        Assert.assertEquals(643, country.getCode().intValue());
        Assert.assertEquals("Российская Федерация", country.getName());
    }

    @Test
    public void testGetByCodeNotFound(){
        Country country = dao.getByCode(0);
        Assert.assertNull(country);
    }
}
