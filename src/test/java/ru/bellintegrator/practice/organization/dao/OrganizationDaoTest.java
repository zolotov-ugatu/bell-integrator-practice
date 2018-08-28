package ru.bellintegrator.practice.organization.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.List;

/**
 * Класс, содержащий тесты методов OrganizationDao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class OrganizationDaoTest {

    @Autowired
    private OrganizationDao dao;

    private Organization organization1;
    private Organization organization2;

    @Before
    public void before(){
        organization1 = new Organization();
        organization1.setName("Первая организация");
        organization1.setFullName("Полное имя первой организации");
        organization1.setInn("1111111111");
        organization1.setKpp("111111111");
        organization1.setPhone("+7 111 111-11-11");
        organization1.setAddress("Россия, г. Москва, ул. Ленина, д. 1");
        organization1.setActive(true);
        dao.save(organization1);
        organization2 = new Organization();
        organization2.setName("Вторая организация");
        organization2.setFullName("Полное имя второй организации");
        organization2.setInn("2222222222");
        organization2.setKpp("222222222");
        organization2.setPhone("+7 222 222-22-22");
        organization2.setAddress("Россия, г. Москва, ул. Ленина, д. 2");
        organization2.setActive(false);
    }

    /**
     * Тест метода list
     */
    @Test
    public void testListMultipleResultByName(){
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(organization1));
        Assert.assertTrue(list.contains(organization2));
    }

    @Test
    public void testListResultByNameLike(){
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("Первая");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization1));
    }

    @Test
    public void testListResultByNameExact(){
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName(organization2.getName());
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization2));
    }

    @Test
    public void testListEmptyResultByName() {
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("ПерваяВторая");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testListSingleResultByInnFirst() {
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        filter.setInn("1111111111");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization1));
    }

    @Test
    public void testListSingleResultByInnSecond() {
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        filter.setInn("2222222222");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization2));
    }

    @Test
    public void testListEmptyResultByInn(){
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        filter.setInn("3333333333");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testListResultByIsActiveFirst(){
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        filter.setActive(organization1.getActive());
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization1));
    }

    @Test
    public void testListResultByIsActiveSecond(){
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        filter.setActive(organization2.getActive());
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization2));
    }

    @Test
    public void testListEmptyResultByIsActive() {
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName(organization1.getName());
        filter.setActive(organization2.getActive());
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    /**
     * Тест метода getById
     */
    @Test
    public void testGetById(){
        Long id = organization1.getId();
        Organization organization = dao.getById(id);
        Assert.assertNotNull(organization);
        Assert.assertSame(organization1, organization);
    }

    /**
     * Тест метода update
     */
    @Test
    public void testUpdate(){
        organization2.setId(organization1.getId());
        dao.update(organization2);
        Assert.assertNotNull(organization1);
        Assert.assertEquals(organization2.getName(), organization1.getName());
        Assert.assertEquals(organization2.getFullName(), organization1.getFullName());
        Assert.assertEquals(organization2.getInn(), organization1.getInn());
        Assert.assertEquals(organization2.getKpp(), organization1.getKpp());
        Assert.assertEquals(organization2.getAddress(), organization1.getAddress());
        Assert.assertEquals(organization2.getPhone(), organization1.getPhone());
        Assert.assertEquals(organization2.getActive(), organization1.getActive());
    }

    /**
     * Тест метода save
     */
    @Test
    public void testSave(){
        dao.save(organization2);
        Assert.assertNotNull(organization2);
        Assert.assertNotNull(organization2.getId());
        Assert.assertSame(organization2, dao.getById(organization2.getId()));
    }

    /**
     * Тест метода remove
     */
    @Test
    public void testRemove(){
        Long id = organization1.getId();
        dao.remove(id);
        Assert.assertNull(dao.getById(id));
    }
}
