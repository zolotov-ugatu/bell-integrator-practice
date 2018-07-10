package ru.bellintegrator.practice.person.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.house.dao.HouseDao;
import ru.bellintegrator.practice.house.model.House;
import ru.bellintegrator.practice.person.model.Person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class HouseDaoTest {

    @Autowired
    private HouseDao houseDao;

    @Test
    public void test() {
        House house = new House();
        Set<Person> list = new HashSet<>();
        house.setAddress("Address");
        Person person = new Person("One", 1);
        person.addHouse(house);
        house.setPersons(list);
        list.add(person);
        houseDao.save(house);

        List<House> houses = houseDao.all();
        Assert.assertNotNull(houses);

        person.addHouse(house);

        Assert.assertFalse(houses.isEmpty());

        Set<Person> persons = houses.get(1).getPersons();

        Assert.assertNotNull(persons);
        Assert.assertEquals(1, persons.size());

        Person person1 = new Person("Two", 2);
        list.add(person1);

        houses = houseDao.all();
        persons = houses.get(1).getPersons();
        Assert.assertNotNull(person);
        Assert.assertEquals(1, persons.size());
    }
}