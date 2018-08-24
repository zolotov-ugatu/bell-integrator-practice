package ru.bellintegrator.practice.user.service;

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
import ru.bellintegrator.practice.ref.countries.dao.CountriesDao;
import ru.bellintegrator.practice.ref.countries.model.Country;
import ru.bellintegrator.practice.ref.docs.dao.DocsDao;
import ru.bellintegrator.practice.ref.docs.model.Doc;
import ru.bellintegrator.practice.user.dao.UserDao;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.view.UserListFilter;
import ru.bellintegrator.practice.user.view.UserToSave;
import ru.bellintegrator.practice.user.view.UserView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserDao userDao;

    @Mock
    private OfficeDao officeDao;

    @Mock
    private DocsDao docsDao;

    @Mock
    private CountriesDao countriesDao;

    private UserView view;
    private UserToSave toSaveView;
    private UserListFilter filter;

    @Before
    public void before() throws ParseException {
        view = new UserView();
        view.id = 1L;
        view.firstName = "Иван";
        view.lastName = "Иванов";
        view.middleName = "Иванович";
        view.officeId = 1L;
        view.position = "Работник";
        view.docCode = 21;
        view.docName = "Паспорт гражданина РФ";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        view.docDate = format.parse("1991-01-01");
        view.docNumber = "AA 111111";
        view.citizenshipCode = 643;
        view.citizenshipName = "Россия";
        view.phone = "+7 000 111 11 11";
        view.isIdentified = true;
        toSaveView = new UserToSave();
        toSaveView.firstName = "Иван";
        toSaveView.lastName = "Иванов";
        toSaveView.middleName = "Иванович";
        toSaveView.officeId = 1L;
        toSaveView.position = "Работник";
        toSaveView.docCode = 21;
        toSaveView.docNumber = "AA 111111";
        format = new SimpleDateFormat("yyyy-MM-dd");
        toSaveView.docDate = format.parse("1991-01-01");
        toSaveView.citizenshipCode = 643;
        toSaveView.phone = "+7 000 111 11 11";
        toSaveView.isIdentified = true;
        filter = new UserListFilter();
        filter.firstName = "Иван";
        filter.lastName = "Иванов";
        filter.middleName = "Иванович";
        filter.officeId = 1L;
        filter.position = "Работник";
        filter.docCode = 21;
        filter.citizenshipCode = 643;
    }

    @Test
    public void testListSuccessResult(){
        User user = new User();
        user.setId(view.id);
        user.setFirstName(view.firstName);
        user.setLastName(view.lastName);
        user.setMiddleName(view.middleName);
        user.getOffice().setId(view.officeId);
        user.setPosition(view.position);
        user.setPhone(view.phone);
        user.getDoc().setCode(view.docCode);
        user.setDocNumber(view.docNumber);
        user.setDocDate(view.docDate);
        user.getCountry().setCode(view.citizenshipCode);
        user.setIdentified(view.isIdentified);
        List<User> daoAnswer = new ArrayList<>();
        daoAnswer.add(user);
        when(userDao.list(any(User.class))).thenReturn(daoAnswer);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        List<UserView> list = service.list(filter);
        verify(userDao, times(1)).list(argument.capture());
        Assert.assertEquals(filter.firstName, argument.getValue().getFirstName());
        Assert.assertEquals(filter.lastName, argument.getValue().getLastName());
        Assert.assertEquals(filter.middleName, argument.getValue().getMiddleName());
        Assert.assertEquals(filter.officeId, argument.getValue().getOffice().getId());
        Assert.assertEquals(filter.position, argument.getValue().getPosition());
        Assert.assertEquals(filter.docCode, argument.getValue().getDoc().getCode());
        Assert.assertEquals(filter.citizenshipCode, argument.getValue().getCountry().getCode());
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(view.id, list.get(0).id);
        Assert.assertEquals(view.firstName, list.get(0).firstName);
        Assert.assertEquals(view.lastName, list.get(0).lastName);
        Assert.assertEquals(view.middleName, list.get(0).middleName);
        Assert.assertEquals(view.position, list.get(0).position);
    }

    @Test
    public void testListEmptyResult(){
        when(userDao.list(any(User.class))).thenReturn(new ArrayList<>());
        List<UserView> list = service.list(filter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test(expected = WrongRequestException.class)
    public void testListThrowsWrongRequestExceptionOfficeIdIsNull(){
        filter.officeId = null;
        service.list(filter);
    }

    @Test
    public void testGetByIdSuccessResult(){
        User user = new User();
        user.setId(view.id);
        user.setFirstName(view.firstName);
        user.setLastName(view.lastName);
        user.setMiddleName(view.middleName);
        user.getOffice().setId(view.officeId);
        user.setPosition(view.position);
        user.setPhone(view.phone);
        user.getDoc().setCode(view.docCode);
        user.getDoc().setName(view.docName);
        user.setDocNumber(view.docNumber);
        user.setDocDate(view.docDate);
        user.getCountry().setCode(view.citizenshipCode);
        user.getCountry().setName(view.citizenshipName);
        user.setIdentified(view.isIdentified);
        when(userDao.getById(view.id)).thenReturn(user);
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        UserView result = service.getById(view.id);
        verify(userDao, times(1)).getById(argument.capture());
        Assert.assertEquals(view.id, argument.getValue());
        Assert.assertEquals(view.firstName, result.firstName);
        Assert.assertEquals(view.lastName, result.lastName);
        Assert.assertEquals(view.middleName, result.middleName);
        Assert.assertEquals(view.position, result.position);
        Assert.assertEquals(view.docNumber, result.docNumber);
        Assert.assertEquals(view.docDate, result.docDate);
        Assert.assertEquals(view.docName, result.docName);
        Assert.assertEquals(view.citizenshipCode, result.citizenshipCode);
        Assert.assertEquals(view.citizenshipName, result.citizenshipName);
        Assert.assertEquals(view.isIdentified, result.isIdentified);
    }

    @Test(expected = WrongRequestException.class)
    public void testGetByIdThrowsWrongRequestExceptionIdIsNull(){
        service.getById(null);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testGetByIdThrowsRecordNotFoundException(){
        when(userDao.getById(view.id)).thenReturn(null);
        service.getById(view.id);
    }

    @Test
    public void testUpdateSuccess(){
        Doc doc = new Doc();
        Country country = new Country();
        when(docsDao.getByCode(view.docCode)).thenReturn(doc);
        when(countriesDao.getByCode(view.citizenshipCode)).thenReturn(country);
        when(userDao.getById(view.id)).thenReturn(new User());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        service.update(view);
        verify(docsDao, atLeastOnce()).getByCode(view.docCode);
        verify(countriesDao, atLeastOnce()).getByCode(view.citizenshipCode);
        verify(userDao, atLeastOnce()).update(argument.capture());
        Assert.assertEquals(view.id, argument.getValue().getId());
        Assert.assertEquals(view.firstName, argument.getValue().getFirstName());
        Assert.assertEquals(view.lastName, argument.getValue().getLastName());
        Assert.assertEquals(view.middleName, argument.getValue().getMiddleName());
        Assert.assertEquals(view.position, argument.getValue().getPosition());
        Assert.assertEquals(view.phone, argument.getValue().getPhone());
        Assert.assertSame(doc, argument.getValue().getDoc());
        Assert.assertEquals(view.docDate, argument.getValue().getDocDate());
        Assert.assertEquals(view.docNumber, argument.getValue().getDocNumber());
        Assert.assertSame(country, argument.getValue().getCountry());
        Assert.assertEquals(view.isIdentified, argument.getValue().getIdentified());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testUpdateThrowsRecordNotFoundException(){
        when(userDao.getById(view.id)).thenReturn(null);
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionIdIsNull(){
        view.id = null;
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionFirstNameIsNull(){
        view.firstName = null;
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionFirstNameIsWrong(){
        view.firstName = "1Иван";
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestPositionIsNull(){
        when(userDao.getById(view.id)).thenReturn(new User());
        when(docsDao.getByCode(view.docCode)).thenReturn(new Doc());
        when(countriesDao.getByCode(view.citizenshipCode)).thenReturn(new Country());
        view.position = null;
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionLastNameIsWrong(){
        view.lastName = "1Иванов";
        service.update(view);
    }

    @Test
    public void testUpdateSuccessMiddleNameIsNull(){
        when(docsDao.getByCode(view.docCode)).thenReturn(new Doc());
        when(countriesDao.getByCode(view.citizenshipCode)).thenReturn(new Country());
        when(userDao.getById(view.id)).thenReturn(new User());
        view.middleName = null;
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionMiddleNameIsWrong(){
        view.middleName = "1Иванович";
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionPositionIsNull(){
        view.position = null;
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionPositionIsWrong(){
        view.position = "1";
        service.update(view);
    }

    @Test
    public void testUpdateSuccessPhoneIsNull(){
        when(docsDao.getByCode(view.docCode)).thenReturn(new Doc());
        when(countriesDao.getByCode(view.citizenshipCode)).thenReturn(new Country());
        when(userDao.getById(view.id)).thenReturn(new User());
        view.phone = null;
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionPhoneIsWrong(){
        view.phone = "phone";
        service.update(view);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testUpdateThrowsRecordNotFoundExceptionNoSuchDoc(){
        when(docsDao.getByCode(view.docCode)).thenReturn(null);
        service.update(view);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testUpdateThrowsRecordNotFoundExceptionNoSuchCountry(){
        when(countriesDao.getByCode(view.citizenshipCode)).thenReturn(null);
        service.update(view);
    }

    @Test(expected = WrongRequestException.class)
    public void testUpdateThrowsWrongRequestExceptionIsIdentifiedIsNull(){
        view.firstName = null;
        service.update(view);
    }

    @Test
    public void testSaveSuccess(){
        Doc doc = new Doc();
        Country country = new Country();
        Office office = new Office();
        when(docsDao.getByCode(toSaveView.docCode)).thenReturn(doc);
        when(countriesDao.getByCode(toSaveView.citizenshipCode)).thenReturn(country);
        when(officeDao.getById(toSaveView.officeId)).thenReturn(office);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        service.save(toSaveView);
        verify(docsDao, atLeastOnce()).getByCode(toSaveView.docCode);
        verify(countriesDao, atLeastOnce()).getByCode(toSaveView.citizenshipCode);
        verify(officeDao, atLeastOnce()).getById(toSaveView.officeId);
        verify(userDao, times(1)).save(argument.capture());
        Assert.assertEquals(toSaveView.firstName, argument.getValue().getFirstName());
        Assert.assertEquals(toSaveView.lastName, argument.getValue().getLastName());
        Assert.assertEquals(toSaveView.middleName, argument.getValue().getMiddleName());
        Assert.assertSame(office, argument.getValue().getOffice());
        Assert.assertEquals(toSaveView.position, argument.getValue().getPosition());
        Assert.assertEquals(toSaveView.phone, argument.getValue().getPhone());
        Assert.assertSame(doc, argument.getValue().getDoc());
        Assert.assertEquals(toSaveView.docNumber, argument.getValue().getDocNumber());
        Assert.assertEquals(toSaveView.docDate, argument.getValue().getDocDate());
        Assert.assertSame(country, argument.getValue().getCountry());
        Assert.assertEquals(toSaveView.isIdentified, argument.getValue().getIdentified());
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionFirstNameIsNull(){
        toSaveView.firstName = null;
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionFirstNameIsWrong(){
        toSaveView.firstName = "1Иван";
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionLastNameIsNull(){
        toSaveView.lastName = null;
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionLastNameIsWrong(){
        toSaveView.lastName = "1Иванов";
        service.save(toSaveView);
    }

    @Test
    public void testSaveSuccessMiddleNameIsNull(){
        when(docsDao.getByCode(toSaveView.docCode)).thenReturn(new Doc());
        when(countriesDao.getByCode(toSaveView.citizenshipCode)).thenReturn(new Country());
        when(officeDao.getById(toSaveView.officeId)).thenReturn(new Office());
        toSaveView.middleName = null;
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionMiddleNameIsWrong(){
        toSaveView.middleName = "1Иванович";
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionPositionIsNull(){
        toSaveView.position = null;
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionPositionIsWrong(){
        toSaveView.position = "1";
        service.save(toSaveView);
    }

    @Test
    public void testSaveSuccessPhoneIsNull(){
        when(docsDao.getByCode(toSaveView.docCode)).thenReturn(new Doc());
        when(countriesDao.getByCode(toSaveView.citizenshipCode)).thenReturn(new Country());
        when(officeDao.getById(toSaveView.officeId)).thenReturn(new Office());
        toSaveView.phone = null;
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionPhoneIsWrong(){
        toSaveView.phone = "phone";
        service.save(toSaveView);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testSaveThrowsRecordNotFoundExceptionNoSuchDoc(){
        when(docsDao.getByCode(view.docCode)).thenReturn(null);
        service.save(toSaveView);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testSaveThrowsRecordNotFoundExceptionNoSuchCountry(){
        when(countriesDao.getByCode(view.citizenshipCode)).thenReturn(null);
        service.save(toSaveView);
    }

    @Test(expected = WrongRequestException.class)
    public void testSaveThrowsWrongRequestExceptionIsIdentifiedIsNull(){
        toSaveView.isIdentified = null;
        service.save(toSaveView);
    }

    @Test
    public void testRemoveSuccess(){
        when(userDao.getById(view.id)).thenReturn(new User());
        when(officeDao.getById(any(Long.class))).thenReturn(new Office());
        service.remove(view.id);
        verify(userDao, times(1)).remove(view.id);
    }

    @Test(expected = WrongRequestException.class)
    public void testRemoveThrowsWrongRequestExceptionIdIsNull(){
        service.remove(null);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testRemoveThrowsRecordNotFoundException(){
        when(userDao.getById(view.id)).thenReturn(null);
        service.remove(view.id);
    }

}
