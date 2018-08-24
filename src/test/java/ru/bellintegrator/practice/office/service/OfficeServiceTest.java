package ru.bellintegrator.practice.office.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;
import ru.bellintegrator.practice.office.dao.OfficeDao;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.view.OfficeListFilter;
import ru.bellintegrator.practice.office.view.OfficeToSave;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.organization.dao.OrganizationDao;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceTest {

    @Mock
    private OfficeDao officeDao;

    @Mock
    private OrganizationDao organizationDao;

    @InjectMocks
    private OfficeServiceImpl officeService;

    private OfficeView view;
    private OfficeListFilter filter;
    private OfficeToSave toSaveView;

    @Before
    public void before(){
        view = new OfficeView();
        view.id = 1L;
        view.name = "Первый офис";
        view.orgId = 1L;
        view.address = "Россия, г. Москва, ул. К. Маркса, д. 1";
        view.phone = "+7 001 111 11 11";
        view.isActive = true;
        filter = new OfficeListFilter();
        filter.name = "Первый офис";
        filter.orgId = 1L;
        filter.phone = "+7 001 111 11 11";
        filter.isActive = true;
        toSaveView = new OfficeToSave();
        toSaveView.name = "Первый офис";
        toSaveView.orgId = 1L;
        toSaveView.address = "Россия, г. Москва, ул. К. Маркса, д. 1";
        toSaveView.phone = "+7 001 111 11 11";
        toSaveView.isActive = true;

    }

    @Test
    public void testListSingleResult(){
        Office office = new Office();
        office.setId(view.id);
        office.setName(view.name);
        office.getOrganization().setId(view.id);
        office.setAddress(view.address);
        office.setPhone(view.phone);
        office.setActive(view.isActive);
        List<Office> daoAnswer = new ArrayList<>();
        daoAnswer.add(office);
        when(officeDao.list(any(Office.class))).thenReturn(daoAnswer);
        ArgumentCaptor<Office> argument = ArgumentCaptor.forClass(Office.class);
        List<OfficeView> list = officeService.list(filter);
        verify(officeDao, times(1)).list(argument.capture());
        Assert.assertEquals(view.orgId, argument.getValue().getOrganization().getId());
        Assert.assertEquals(view.name, argument.getValue().getName());
        Assert.assertEquals(view.phone, argument.getValue().getPhone());
        Assert.assertEquals(view.isActive, argument.getValue().getActive());
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view.id, list.get(0).id);
        Assert.assertEquals(view.name, list.get(0).name);
        Assert.assertEquals(view.isActive, list.get(0).isActive);
    }

    @Test
    public void testListEmptyResult(){
        when(officeDao.list(any())).thenReturn(new ArrayList<>());
        List<OfficeView> list = officeService.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test(expected = WrongRequestException.class)
    public void testListThrowsWrongRequestExceptionOrgIdIsNull(){
        filter.orgId = null;
        officeService.list(filter);
    }

    @Test
    public void testGetByIdSuccess(){
        Office office = new Office();
        office.setId(view.id);
        office.setName(view.name);
        office.getOrganization().setId(view.id);
        office.setAddress(view.address);
        office.setPhone(view.phone);
        office.setActive(view.isActive);
        when(officeDao.getById(view.id)).thenReturn(office);
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        OfficeView result = officeService.getById(view.id);
        verify(officeDao, times(1)).getById(argument.capture());
        Assert.assertEquals(view.id, argument.getValue());
        Assert.assertEquals(view.id, result.id);
        Assert.assertEquals(view.name, result.name);
        Assert.assertEquals(view.orgId, result.orgId);
        Assert.assertEquals(view.address, result.address);
        Assert.assertEquals(view.phone, result.phone);
        Assert.assertEquals(view.isActive, result.isActive);
    }

    @Test(expected = WrongRequestException.class)
    public void testGetByIdThrowsWrongRequestExceptionIdIsNull(){
        officeService.getById(null);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testGetByIdThrowsRecordNotFoundException(){
        when(officeDao.getById(view.id)).thenReturn(null);
        officeService.getById(view.id);
    }

    @Test
    public void testUpdateSuccess(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        ArgumentCaptor<Office> argument = ArgumentCaptor.forClass(Office.class);
        officeService.update(view);
        verify(officeDao, times(1)).update(argument.capture());
        Assert.assertEquals(view.id, argument.getValue().getId());
        Assert.assertEquals(view.name, argument.getValue().getName());
        Assert.assertEquals(view.address, argument.getValue().getAddress());
        Assert.assertEquals(view.phone, argument.getValue().getPhone());
        Assert.assertEquals(view.isActive, argument.getValue().getActive());
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionIdIsNull(){
        view.id = null;
        officeService.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionAddressIsNull(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        view.address = null;
        officeService.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionAddressIsInvalid(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        view.address = "";
        officeService.update(view);
    }

    @Test
    public void testUpdateSuccessPhoneIsNull(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        view.phone = null;
        officeService.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionPhoneIsInvalid(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        view.phone = "phone";
        officeService.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionNameIsNull(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        view.name = null;
        officeService.update(view);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testUpdateThrowsRecordNotFoundException(){
        when(officeDao.getById(view.id)).thenReturn(null);
        officeService.update(view);
    }

    @Test
    public void testSaveSuccess(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        ArgumentCaptor<Office> argument = ArgumentCaptor.forClass(Office.class);
        officeService.save(toSaveView);
        verify(officeDao, times(1)).save(argument.capture());
        Assert.assertEquals(toSaveView.name, argument.getValue().getName());
        Assert.assertEquals(toSaveView.orgId, argument.getValue().getOrganization().getId());
        Assert.assertEquals(toSaveView.address, argument.getValue().getAddress());
        Assert.assertEquals(toSaveView.phone, argument.getValue().getPhone());
        Assert.assertEquals(toSaveView.isActive, argument.getValue().getActive());
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionNameIsWrong(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.name = "111";
        officeService.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionNameIsNull(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.name = null;
        officeService.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionAddressIsWrong(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.address = "";
        officeService.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionAddressIsNull(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.address = null;
        officeService.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionPhoneIsWrong(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.phone = "phone";
        officeService.save(toSaveView);
    }

    @Test
    public void testSaveSuccessPhoneIsNull(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.phone = null;
        officeService.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionIsActiveIsNull(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.isActive = null;
        officeService.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionOrgIdIsWrong(){
        Organization organization = new Organization();
        organization.setId(view.orgId);
        when(organizationDao.getById(view.orgId)).thenReturn(organization);
        toSaveView.orgId = null;
        officeService.save(toSaveView);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testSaveThrowsRecordNotFoundExceptionOrgIdIsWrong(){
        when(organizationDao.getById(view.orgId)).thenReturn(null);
        officeService.save(toSaveView);
    }

    @Test
    public void testRemoveSuccess(){
        when(officeDao.getById(view.id)).thenReturn(new Office());
        when(organizationDao.getById(any(Long.class))).thenReturn(new Organization());
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        officeService.remove(view.id);
        verify(officeDao, times(1)).remove(argument.capture());
        Assert.assertEquals(view.id, argument.getValue());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testRemoveThrowsRecordNotFoundException(){
        when(officeDao.getById(view.id)).thenReturn(null);
        officeService.remove(view.id);
    }

    @Test(expected = WrongRequestException.class)
    public void testRemoveThrowsWrongRequestException(){
        officeService.remove(null);
    }
}