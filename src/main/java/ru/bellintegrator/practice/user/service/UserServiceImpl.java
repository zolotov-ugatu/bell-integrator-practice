package ru.bellintegrator.practice.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;
import ru.bellintegrator.practice.office.dao.OfficeDao;
import ru.bellintegrator.practice.ref.countries.dao.CountriesDao;
import ru.bellintegrator.practice.ref.countries.model.Country;
import ru.bellintegrator.practice.ref.docs.dao.DocsDao;
import ru.bellintegrator.practice.user.dao.UserDao;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.view.UserListFilter;
import ru.bellintegrator.practice.user.view.UserToSave;
import ru.bellintegrator.practice.user.view.UserView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final OfficeDao officeDao;
    private final DocsDao docsDao;
    private final CountriesDao countriesDao;

    /**
     * Конструктор
     *
     * @param userDao DAO пользователей
     * @param officeDao DAO оффисов
     * @param docsDao DAO документов
     * @param countriesDao DAO стран
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, OfficeDao officeDao, DocsDao docsDao, CountriesDao countriesDao){
        this.userDao = userDao;
        this.officeDao = officeDao;
        this.docsDao = docsDao;
        this.countriesDao = countriesDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserView> list(UserListFilter filter) {
        validateFilter(filter);
        User user = new User();
        user.setFirstName(filter.firstName);
        user.setLastName(filter.lastName);
        user.setMiddleName(filter.middleName);
        user.getOffice().setId(filter.officeId);
        user.setPosition(filter.position);
        user.getDoc().setCode(filter.docCode);
        user.getCountry().setCode(filter.citizenshipCode);
        List<User> list = userDao.list(user);
        return list.stream().map(mapUser()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserView getById(Long userId) {
        if (userId == null){
            throw new WrongRequestException("\"id\" is null.");
        }
        User user = userDao.getById(userId);
        UserView view = new UserView();
        view.id = user.getId();
        view.firstName = user.getFirstName();
        view.lastName = user.getLastName();
        view.middleName = user.getMiddleName();
        view.position = user.getPosition();
        view.phone = user.getPhone();
        view.docName = user.getDoc().getName();
        view.docNumber = user.getDocNumber();
        view.docDate = user.getDocDate();
        view.citizenshipName = user.getCountry().getName();
        view.citizenshipCode = user.getCountry().getCode();
        view.isIdentified = user.getIdentified();
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(UserView view) {
        validateView(view);
        User user = new User();
        user.setId(view.id);
        user.setFirstName(view.firstName);
        user.setLastName(view.lastName);
        user.setMiddleName(view.middleName);
        user.setPosition(view.position);
        user.setPhone(view.phone);
        user.setDoc(docsDao.getByCode(view.docCode));
        user.setDocNumber(view.docNumber);
        user.setDocDate(view.docDate);
        user.setCountry(countriesDao.getByCode(view.citizenshipCode));
        user.setIdentified(view.isIdentified);
        userDao.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(UserToSave view) {
        validateToSaveView(view);
        User user = new User();
        user.setFirstName(view.firstName);
        user.setLastName(view.lastName);
        user.setMiddleName(view.middleName);
        user.setOffice(officeDao.getById(view.officeId));
        user.setPosition(view.position);
        user.setPhone(view.phone);
        user.setDoc(docsDao.getByCode(view.docCode));
        user.setDocNumber(view.docNumber);
        user.setDocDate(view.docDate);
        user.setCountry(countriesDao.getByCode(view.citizenshipCode));
        user.setIdentified(view.isIdentified);
        userDao.save(user);
    }

    private boolean isFirstNameValid(String firstName){
        return firstName.matches("[A-ZА-Я][a-zа-я]{1,49}");
    }

    private boolean isLastNameValid(String lastName){
        return lastName.matches("[A-ZА-Я][A-Za-zА-Яа-я'-]{1,49}");
    }

    private boolean isMiddleNameValid(String middleName){
        return middleName.matches("[A-ZА-Я][A-Za-zА-Яа-я '-]{1,49}");
    }

    private boolean isPositionValid(String position){
        return position.matches("[A-Za-zА-Яа-я ,-]{1,50}");
    }

    private boolean isDocNumberValid(String docNubmer){
        return docNubmer.matches("[A-Za-zА-Яа-я0-9 -]{1,20}");
    }

    private boolean isPhoneValid(String phone){
        return phone.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
    }

    private void validateFilter(UserListFilter filter){
        if (filter.officeId == null){
            throw new WrongRequestException("Field \"officeId\" is null.");
        }
    }

    private void validateView(UserView view){
        StringBuilder messageBuilder = new StringBuilder("");
        if (view.id == null){
            messageBuilder.append("Field \"id\" is null. ");
        }
        if (view.firstName == null || !isFirstNameValid(view.firstName)){
            messageBuilder.append("Field \"firstName\" is null or invalid. ");
        }
        if (view.lastName == null || !isLastNameValid(view.lastName)){
            messageBuilder.append("Field \"lastName\" is null or invalid. ");
        }
        if (view.middleName != null && !isMiddleNameValid(view.middleName)){
            messageBuilder.append("Field \"middleName\" is invalid. ");
        }
        if (view.position == null || !isPositionValid(view.position)){
            messageBuilder.append("Field \"position\" is null or invalid. ");
        }
        if (view.phone == null || !isPhoneValid(view.phone)){
            messageBuilder.append("Field \"phone\" is null or invalid. ");
        }
        if (view.docCode == null){
            messageBuilder.append("Field \"docCode\" is null . ");
        }
        if (view.docNumber == null || !isDocNumberValid(view.docNumber)){
            messageBuilder.append("Field \"docNumber\" is null or invalid. ");
        }
        if (view.docDate == null){
            messageBuilder.append("Field \"docDate\" is null. ");
        }
        if (view.citizenshipCode == null){
            messageBuilder.append("Field \"citizenshipCode\" is null. ");
        }
        if (view.isIdentified == null){
            messageBuilder.append("Field \"isIdentified\" is null.");
        }
        if (messageBuilder.length() > 0){
            throw new WrongRequestException(messageBuilder.toString().trim());
        }
        if (docsDao.getByCode(view.docCode) == null){
            messageBuilder.append("Doc with code = ").append(view.docCode).append(" was not found. ");
        }
        if (countriesDao.getByCode(view.citizenshipCode) == null){
            messageBuilder.append("Country with code = ").append(view.citizenshipCode).append(" was not found.");
        }
        if (messageBuilder.length() > 0){
            throw new RecordNotFoundException(messageBuilder.toString().trim());
        }
    }

    private void validateToSaveView(UserToSave view){
        StringBuilder messageBuilder = new StringBuilder("");
        if (view.firstName == null || !isFirstNameValid(view.firstName)){
            messageBuilder.append("Field \"firstName\" is null or invalid. ");
        }
        if (view.lastName == null || !isLastNameValid(view.lastName)){
            messageBuilder.append("Field \"lastName\" is null or invalid. ");
        }
        if (view.middleName != null && !isMiddleNameValid(view.middleName)){
            messageBuilder.append("Field \"middleName\" is invalid. ");
        }
        if (view.officeId == null){
            messageBuilder.append("Field \"officeId\" is null. ");
        }
        if (view.position == null || !isPositionValid(view.position)){
            messageBuilder.append("Field \"position\" is null or invalid. ");
        }
        if (view.phone == null || !isPhoneValid(view.phone)){
            messageBuilder.append("Field \"phone\" is null or invalid. ");
        }
        if (view.docCode == null){
            messageBuilder.append("Field \"docCode\" is null . ");
        }
        if (view.docNumber == null || !isDocNumberValid(view.docNumber)){
            messageBuilder.append("Field \"docNumber\" is null or invalid. ");
        }
        if (view.docDate == null){
            messageBuilder.append("Field \"docDate\" is null. ");
        }
        if (view.citizenshipCode == null){
            messageBuilder.append("Field \"citizenshipCode\" is null. ");
        }
        if (view.isIdentified == null){
            messageBuilder.append("Field \"isIdentified\" is null.");
        }
        if (messageBuilder.length() > 0){
            throw new WrongRequestException(messageBuilder.toString().trim());
        }
        if (officeDao.getById(view.officeId) == null){
            messageBuilder.append("Office with id = ").append(view.officeId).append(" was not found. ");
        }
        if (docsDao.getByCode(view.docCode) == null){
            messageBuilder.append("Doc with code = ").append(view.docCode).append(" was not found. ");
        }
        if (countriesDao.getByCode(view.citizenshipCode) == null){
            messageBuilder.append("Country with code = ").append(view.citizenshipCode).append(" was not found.");
        }
        if (messageBuilder.length() > 0){
            throw new RecordNotFoundException(messageBuilder.toString().trim());
        }
    }

    private Function<User, UserView> mapUser(){
        return u -> {
            UserView view = new UserView();
            view.id = u.getId();
            view.firstName = u.getFirstName();
            view.lastName = u.getLastName();
            view.middleName = u.getMiddleName();
            view.position = u.getPosition();
            return view;
        };
    }
}
