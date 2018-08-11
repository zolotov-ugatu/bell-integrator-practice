package ru.bellintegrator.practice.organization.dao;

import org.junit.After;
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
import ru.bellintegrator.practice.organization.view.OrganizationListFilter;

import java.util.List;

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
        organization2 = new Organization();
        organization2.setName("Вторая организация");
        organization2.setFullName("Полное имя второй организации");
        organization2.setInn("2222222222");
        organization2.setKpp("222222222");
        organization2.setPhone("+7 222 222-22-22");
        organization2.setAddress("Россия, г. Москва, ул. Ленина, д. 2");
        organization2.setActive(false);
    }

    @Test
    public void testList(){
        dao.save(organization1);
        dao.save(organization2);
        Organization filter = new Organization();
        filter.setName("организация");
        List<Organization> list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(organization1));
        Assert.assertTrue(list.contains(organization2));
        filter.setName("Первая");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization1));
        filter.setName("Вторая");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization2));
        filter.setName("ПерваяВторая");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
        filter.setName("организация");
        filter.setInn("1111111111");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization1));
        Assert.assertFalse(list.contains(organization2));
        filter.setInn("2222222222");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization2));
        filter.setName("Первая");
        filter.setInn("2222222222");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
        filter.setName("организация");
        filter.setInn(null);
        filter.setActive(true);
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization1));
        Assert.assertFalse(list.contains(organization2));
        filter.setActive(false);
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(organization2));
        filter.setName("Первая");
        list = dao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
        dao.remove(organization1.getId());
        dao.remove(organization2.getId());
    }

    @Test
    public void testGetById(){
        dao.save(organization1);
        Long id = organization1.getId();
        Organization organization = dao.getById(id);
        Assert.assertNotNull(organization);
        Assert.assertSame(organization1, organization);
        dao.remove(id);
    }

    @Test
    public void testUpdate(){
        dao.save(organization1);
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
        dao.remove(organization1.getId());
    }

    @Test
    public void testSave(){
        dao.save(organization1);
        Assert.assertNotNull(organization1);
        Assert.assertNotNull(organization1.getId());
        Assert.assertSame(organization1, dao.getById(organization1.getId()));
        dao.remove(organization1.getId());
    }

    @Test
    public void testRemove(){
        dao.save(organization1);
        Long id = organization1.getId();
        dao.remove(organization1.getId());
        Assert.assertNull(dao.getById(id));
    }
}
