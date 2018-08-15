package ru.bellintegrator.practice.organization.service;

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
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;
import ru.bellintegrator.practice.organization.view.OrganizationListFilter;
import ru.bellintegrator.practice.organization.view.OrganizationToSave;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;

/**
 * Класс, содержащий тесты методов OrganizationService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService service;

    private OrganizationToSave view1;
    private OrganizationToSave view2;
    private OrganizationListFilter filterView;

    @Before
    public void before(){
        view1 = new OrganizationToSave();
        view1.name = "Первая организация";
        view1.fullName = "Полное название первой организации";
        view1.inn = "1111111111";
        view1.kpp = "111111111";
        view1.phone = "+7 111 111-11-11";
        view1.address = "Россия, г. Москва, ул. Ленина, д. 1";
        view1.isActive = true;
        view2 = new OrganizationToSave();
        view2.name = "Вторая организация";
        view2.fullName = "Полное название второй организации";
        view2.inn = "2222222222";
        view2.kpp = "222222222";
        view2.phone = "+7 222 222-22-22";
        view2.address = "Россия, г. Москва, ул. Ленина, д. 2";
        view2.isActive = false;
        filterView = new OrganizationListFilter();
    }

    @Test
    public void testListMultipleResultByName(){
        service.save(view1);
        service.save(view2);
        filterView.name = "организация";
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.get(0).name.contains("организация"));
        Assert.assertTrue(list.get(1).name.contains("организация"));
    }

    @Test
    public void testListSingleResultByNameLike(){
        service.save(view1);
        service.save(view2);
        filterView.name = "Первая";
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view1.name, list.get(0).name);
        Assert.assertEquals(view1.fullName, list.get(0).fullName);
        Assert.assertEquals(view1.inn, list.get(0).inn);
        Assert.assertEquals(view1.kpp, list.get(0).kpp);
        Assert.assertEquals(view1.address, list.get(0).address);
        Assert.assertEquals(view1.phone, list.get(0).phone);
        Assert.assertEquals(view1.isActive, list.get(0).isActive);
    }

    @Test
    public void testListSingleResultByNameExact(){
        service.save(view1);
        service.save(view2);
        filterView.name = view2.name;
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view2.name, list.get(0).name);
        Assert.assertEquals(view2.fullName, list.get(0).fullName);
        Assert.assertEquals(view2.inn, list.get(0).inn);
        Assert.assertEquals(view2.kpp, list.get(0).kpp);
        Assert.assertEquals(view2.address, list.get(0).address);
        Assert.assertEquals(view2.phone, list.get(0).phone);
        Assert.assertEquals(view2.isActive, list.get(0).isActive);
    }

    @Test
    public void testListSingleResultByInn(){
        service.save(view1);
        service.save(view2);
        filterView.name = "организация";
        filterView.inn = view1.inn;
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view1.name, list.get(0).name);
        Assert.assertEquals(view1.fullName, list.get(0).fullName);
        Assert.assertEquals(view1.inn, list.get(0).inn);
        Assert.assertEquals(view1.kpp, list.get(0).kpp);
        Assert.assertEquals(view1.address, list.get(0).address);
        Assert.assertEquals(view1.phone, list.get(0).phone);
        Assert.assertEquals(view1.isActive, list.get(0).isActive);
    }

    @Test
    public void testListEmptyResultByInn(){
        service.save(view1);
        service.save(view2);
        filterView.name = view1.name;
        filterView.inn = view2.inn;
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testListResultByIsActiveTrue(){
        service.save(view1);
        service.save(view2);
        filterView.name = "организация";
        filterView.isActive = view1.isActive;
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view1.name, list.get(0).name);
        Assert.assertEquals(view1.fullName, list.get(0).fullName);
        Assert.assertEquals(view1.inn, list.get(0).inn);
        Assert.assertEquals(view1.kpp, list.get(0).kpp);
        Assert.assertEquals(view1.address, list.get(0).address);
        Assert.assertEquals(view1.phone, list.get(0).phone);
        Assert.assertEquals(view1.isActive, list.get(0).isActive);
    }

    @Test
    public void testListResultByIsActiveFalse(){
        service.save(view1);
        service.save(view2);
        filterView.name = "организация";
        filterView.isActive = view2.isActive;
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view2.name, list.get(0).name);
        Assert.assertEquals(view2.fullName, list.get(0).fullName);
        Assert.assertEquals(view2.inn, list.get(0).inn);
        Assert.assertEquals(view2.kpp, list.get(0).kpp);
        Assert.assertEquals(view2.address, list.get(0).address);
        Assert.assertEquals(view2.phone, list.get(0).phone);
        Assert.assertEquals(view2.isActive, list.get(0).isActive);
    }

    @Test
    public void testListEmptyResultByIsActive(){
        service.save(view1);
        service.save(view2);
        filterView.name = view1.name;
        filterView.isActive = view2.isActive;
        List<OrganizationView> list = service.list(filterView);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test(expected = WrongRequestException.class)
    public void testListThrowsWrongRequestException(){
        filterView.inn = view1.inn;
        filterView.isActive= true;
        service.list(filterView);
    }

    @Test
    public void testGetById(){
        service.save(view1);
        filterView.name = view1.name;
        filterView.inn = view1.inn;
        filterView.isActive = view1.isActive;
        OrganizationView view = service.list(filterView).get(0);
        Long id = view.id;
        view = service.getById(id);
        Assert.assertEquals(id, view.id);
        Assert.assertEquals(view1.name, view.name);
        Assert.assertEquals(view1.fullName, view.fullName);
        Assert.assertEquals(view1.inn, view.inn);
        Assert.assertEquals(view1.kpp, view.kpp);
        Assert.assertEquals(view1.address, view.address);
        Assert.assertEquals(view1.phone, view.phone);
        Assert.assertEquals(view1.isActive, view.isActive);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testGetByIdThrowsRecordNotFoundException(){
        service.save(view1);
        filterView.name = view1.name;
        filterView.inn = view1.inn;
        filterView.isActive = view1.isActive;
        Long id = service.list(filterView).get(0).id;
        service.getById(++id);
    }

    @Test(expected = WrongRequestException.class)
    public void testGetByIdThrowsWrongRequestException(){
        service.getById(null);
    }

    @Test
    public void testUpdate(){
        service.save(view1);
        filterView.name = view1.name;
        OrganizationView view = new OrganizationView();
        Long id = service.list(filterView).get(0).id;
        view.id = id;
        view.name = view2.name;
        view.fullName = view2.fullName;
        view.inn = view2.inn;
        view.kpp = view2.kpp;
        view.address = view2.address;
        view.phone = view2.phone;
        view.isActive = view2.isActive;
        service.update(view);
        view = service.getById(id);
        Assert.assertEquals(id, view.id);
        Assert.assertEquals(view2.name, view.name);
        Assert.assertEquals(view2.fullName, view.fullName);
        Assert.assertEquals(view2.inn, view.inn);
        Assert.assertEquals(view2.kpp, view.kpp);
        Assert.assertEquals(view2.address, view.address);
        Assert.assertEquals(view2.phone, view.phone);
        Assert.assertEquals(view2.isActive, view.isActive);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestException(){
        service.save(view1);
        filterView.name = view1.name;
        OrganizationView view = new OrganizationView();
        view.id = service.list(filterView).get(0).id;
        service.update(view);
    }

    @Test
    public void testSave(){
        filterView.name = view1.name;
        filterView.inn = view1.inn;
        filterView.isActive = view1.isActive;
        Assert.assertTrue(service.list(filterView).isEmpty());
        service.save(view1);
        Assert.assertEquals(1, service.list(filterView).size());
        OrganizationView view = service.getById(service.list(filterView).get(0).id);
        Assert.assertEquals(view1.name, view.name);
        Assert.assertEquals(view1.fullName, view.fullName);
        Assert.assertEquals(view1.inn, view.inn);
        Assert.assertEquals(view1.kpp, view.kpp);
        Assert.assertEquals(view1.address, view.address);
        Assert.assertEquals(view1.phone, view.phone);
        Assert.assertEquals(view1.isActive, view.isActive);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestException(){
        view1.name = null;
        service.save(view1);
    }

    @Test
    public void testRemove(){
        service.save(view1);
        filterView.name = view1.name;
        filterView.inn = view1.inn;
        filterView.isActive = view1.isActive;
        Long id = service.list(filterView).get(0).id;
        service.remove(id);
        Assert.assertTrue(service.list(filterView).isEmpty());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testRemoveThrowsRecordNotFoundException(){
        service.save(view1);
        filterView.name = view1.name;
        filterView.inn = view1.inn;
        filterView.isActive = view1.isActive;
        Long id = service.list(filterView).get(0).id;
        service.remove(id);
        service.remove(id);
    }

    @Test(expected = WrongRequestException.class)
    public void testRemoveThrowsWrongRequestException(){
        service.remove(null);
    }
}
