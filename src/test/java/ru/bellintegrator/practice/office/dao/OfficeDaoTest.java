package ru.bellintegrator.practice.office.dao;

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
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.organization.dao.OrganizationDao;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.List;

/**
 * Класс, содержащий тесты методов OfficeDao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class OfficeDaoTest {

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private OrganizationDao organizationDao;

    private Office office1;
    private Office office2;
    private Office filter;
    private Organization organization;

    @Before
    public void before(){
        organization = new Organization();
        organization.setName("Первая организация");
        organization.setFullName("Полное имя первой организации");
        organization.setInn("1111111111");
        organization.setKpp("111111111");
        organization.setPhone("+7 111 111-11-11");
        organization.setAddress("Россия, г. Москва, ул. Ленина, д. 1");
        organization.setActive(true);
        organizationDao.save(organization);
        office1 = new Office();
        office1.setName("Первый офис");
        office1.setAddress("Россия, г. Москва, ул. К. Маркса, д. 1");
        office1.setPhone("+7 001 111 11 11");
        office1.setActive(true);
        organization.addOffice(office1);
        officeDao.save(office1);
        office2 = new Office();
        office2.setName("Второй офис");
        office2.setAddress("Россия, г. Москва, ул. К. Маркса, д. 2");
        office2.setPhone("+7 001 222 22 22");
        office2.setActive(false);
        filter = new Office();
    }

    @Test
    public void testListSingleResultByOrgId(){
        filter.getOrganization().setId(organization.getId());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertSame(office1, list.get(0));
    }

    @Test
    public void testListMultipleResultByOrgId(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(office1));
        Assert.assertTrue(list.contains(office2));
    }

    @Test
    public void testListResultByNameLike(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        filter.setName("офис");
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(office1));
        Assert.assertTrue(list.contains(office2));
    }

    @Test
    public void testListResultByNameExact(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        filter.setName(office1.getName());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertSame(office1, list.get(0));
    }

    @Test
    public void testListResultByPhone(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        filter.setName("офис");
        filter.setPhone(office1.getPhone());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertSame(office1, list.get(0));
    }

    @Test
    public void testListEmptyResultByPhone(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        filter.setName(office1.getName());
        filter.setPhone(office2.getPhone());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testListResultByIsActive(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        filter.setName("офис");
        filter.setActive(office1.getActive());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertSame(office1, list.get(0));
    }

    @Test
    public void testListEmptyResultByIsActive(){
        organization.addOffice(office2);
        officeDao.save(office2);
        filter.getOrganization().setId(organization.getId());
        filter.setName(office1.getName());
        filter.setActive(office2.getActive());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testListResultByAllParameters(){
        filter.getOrganization().setId(organization.getId());
        filter.setName(office1.getName());
        filter.setPhone(office1.getPhone());
        filter.setActive(office1.getActive());
        List<Office> list = officeDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertSame(office1, list.get(0));
    }

    @Test
    public void testGetById(){
        Office result = officeDao.getById(office1.getId());
        Assert.assertNotNull(result);
        Assert.assertSame(office1, result);
    }

    @Test
    public void testGetByIdNullResult(){
        Office result = officeDao.getById(office1.getId() + 1);
        Assert.assertNull(result);
    }

    @Test
    public void testUpdate(){
        office2.setId(office1.getId());
        officeDao.update(office2);
        Office updated = officeDao.getById(office1.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(office2.getName(), updated.getName());
        Assert.assertEquals(office2.getAddress(), updated.getAddress());
        Assert.assertEquals(office2.getPhone(), updated.getPhone());
        Assert.assertEquals(office2.getActive(), updated.getActive());
    }

    @Test
    public void testSave(){
        organization.addOffice(office2);
        officeDao.save(office2);
        Assert.assertNotNull(office2.getId());
        Assert.assertSame(officeDao.getById(office2.getId()), office2);
    }

    @Test
    public void testRemove(){
        Long id = office1.getId();
        officeDao.remove(office1.getId());
        Assert.assertNull(officeDao.getById(id));
    }
}
