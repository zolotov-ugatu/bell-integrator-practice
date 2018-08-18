package ru.bellintegrator.practice.user.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.office.dao.OfficeDao;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.organization.dao.OrganizationDao;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.ref.countries.dao.CountriesDao;
import ru.bellintegrator.practice.ref.countries.model.Country;
import ru.bellintegrator.practice.ref.docs.dao.DocsDao;
import ru.bellintegrator.practice.ref.docs.model.Doc;
import ru.bellintegrator.practice.user.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private OrganizationDao organizationDao;

    @MockBean
    private DocsDao docsDao;

    @MockBean
    private CountriesDao countriesDao;

    private User user1;
    private User user2;
    private User filter;
    private Office office;

    @Before
    public void before() throws ParseException {
        Doc doc = new Doc();
        doc.setId(1);
        doc.setCode(21);
        doc.setName("Паспорт гражданина РФ");
        when(docsDao.getByCode(21)).thenReturn(doc);
        Country country = new Country();
        country.setId(1);
        country.setCode(643);
        country.setName("Россия");
        when(countriesDao.getByCode(643)).thenReturn(country);
        Organization organization = new Organization();
        organization.setName("Первая организация");
        organization.setFullName("Полное имя первой организации");
        organization.setInn("1111111111");
        organization.setKpp("111111111");
        organization.setPhone("+7 111 111-11-11");
        organization.setAddress("Россия, г. Москва, ул. Ленина, д. 1");
        organization.setActive(true);
        organizationDao.save(organization);
        office = new Office();
        office.setName("Первый офис");
        office.setAddress("Россия, г. Москва, ул. К. Маркса, д. 1");
        office.setPhone("+7 001 111 11 11");
        office.setActive(true);
        organization.addOffice(office);
        officeDao.save(office);
        user1 = new User();
        user1.setFirstName("Иван");
        user1.setLastName("Иванов");
        user1.setMiddleName("Иванович");
        user1.setPosition("Работник");
        user1.setDoc(docsDao.getByCode(21));
        user1.setDocNumber("AA 111111");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        user1.setDocDate(format.parse("1991-01-01"));
        user1.setCountry(countriesDao.getByCode(643));
        user1.setPhone("+7 000 111 11 11");
        user1.setIdentified(true);
        office.addUser(user1);
        userDao.save(user1);
        user2 = new User();
        user2.setFirstName("Петр");
        user2.setLastName("Петров");
        user2.setMiddleName("Петрович");
        user2.setPosition("Директор");
        user2.setDoc(docsDao.getByCode(21));
        user2.setDocNumber("BB 222222");
        user2.setDocDate(format.parse("1992-02-02"));
        user2.setCountry(countriesDao.getByCode(643));
        user2.setPhone("+7 000 222 22 22");
        user2.setIdentified(false);
        filter = new User();
    }

    @Test
    public void testListMultipleResultByOfficeId(){
        office.addUser(user2);
        userDao.save(user2);
        filter.getOffice().setId(office.getId());
        List<User> list = userDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(user1));
        Assert.assertTrue(list.contains(user2));
    }

    @Test
    public void testListSingleResultByOfficeIdAndPosition(){
        office.addUser(user2);
        userDao.save(user2);
        filter.getOffice().setId(office.getId());
        filter.setPosition(user2.getPosition());
        List<User> list = userDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(user2));
    }

    @Test
    public void testListEmptyResultByNames(){
        office.addUser(user2);
        userDao.save(user2);
        filter.getOffice().setId(office.getId());
        filter.setLastName(user1.getLastName());
        filter.setMiddleName(user2.getMiddleName());
        List<User> list = userDao.list(filter);
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testGetByIdSuccessResult(){
        User user = userDao.getById(user1.getId());
        Assert.assertNotNull(user);
        Assert.assertSame(user1, user);
    }

    @Test
    public void testGetByIdNullResult(){
        User user = userDao.getById(user1.getId() + 1);
        Assert.assertNull(user);
    }

    @Test
    public void testUpdate(){
        user2.setId(user1.getId());
        userDao.update(user2);
        User updated = userDao.getById(user2.getId());
        Assert.assertEquals(user2.getId(), updated.getId());
        Assert.assertEquals(user2.getFirstName(), updated.getFirstName());
        Assert.assertEquals(user2.getMiddleName(), updated.getMiddleName());
        Assert.assertEquals(user2.getPosition(), updated.getPosition());
        Assert.assertEquals(user2.getDoc().getCode(), updated.getDoc().getCode());
        Assert.assertEquals(user2.getDocNumber(), updated.getDocNumber());
        Assert.assertEquals(user2.getDocDate(), updated.getDocDate());
        Assert.assertEquals(user2.getCountry().getCode(), updated.getCountry().getCode());
        Assert.assertEquals(user2.getPhone(), updated.getPhone());
        Assert.assertEquals(user2.getIdentified(), updated.getIdentified());
    }

    @Test
    public void testSave(){
        office.addUser(user2);
        userDao.save(user2);
        Assert.assertNotNull(user2.getId());
        Assert.assertSame(user2, userDao.getById(user2.getId()));
    }

    @Test
    public void testRemove(){
        Long id = user1.getId();
        userDao.remove(user1.getId());
        Assert.assertNull(userDao.getById(id));
    }
}
