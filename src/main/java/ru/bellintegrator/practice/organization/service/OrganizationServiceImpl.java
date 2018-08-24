package ru.bellintegrator.practice.organization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;
import ru.bellintegrator.practice.organization.dao.OrganizationDao;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.view.OrganizationListFilter;
import ru.bellintegrator.practice.organization.view.OrganizationToSave;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OrganizationDao dao;

    /**
     * Конструктор
     *
     * @param dao DAO организаций
     */
    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao){
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationView> list(OrganizationListFilter filterView) {
        validateFilter(filterView);
        Organization filter = new Organization();
        filter.setName(filterView.name);
        filter.setInn(filterView.inn);
        filter.setActive(filterView.isActive);
        List<Organization> list = dao.list(filter);
        return list.stream().map(mapOrganization()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationView getById(Long id) {
        if (id == null){
            throw new WrongRequestException("Field \"id\" is null.");
        }
        Organization organization = dao.getById(id);
        if (organization == null){
            throw new RecordNotFoundException("Record with id = " + id + " was not found in Organization.");
        }
        OrganizationView organizationView = new OrganizationView();
        organizationView.id = organization.getId();
        organizationView.name = organization.getName();
        organizationView.fullName = organization.getFullName();
        organizationView.inn = organization.getInn();
        organizationView.kpp = organization.getKpp();
        organizationView.address = organization.getAddress();
        organizationView.phone = organization.getPhone();
        organizationView.isActive = organization.getActive();
        return organizationView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(OrganizationView view) {
        validateView(view);
        Organization organization = new Organization();
        organization.setId(view.id);
        organization.setName(view.name);
        organization.setFullName(view.fullName);
        organization.setInn(view.inn);
        organization.setKpp(view.kpp);
        organization.setAddress(view.address);
        if (view.phone != null){
            organization.setPhone(view.phone == "" ? null : view.phone);
        }
        else {
            organization.setPhone(dao.getById(view.id).getPhone());
        }
        organization.setActive(view.isActive != null ? view.isActive : dao.getById(view.id).getActive());
        dao.update(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(OrganizationToSave view) {
        validateToSaveView(view);
        Organization organization = new Organization(view.name, view.fullName, view.inn,
                view.kpp, view.address, view.phone, view.isActive);
        dao.save(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void remove(Long id){
        if (id == null) {
            throw new WrongRequestException("Id is null.");
        }
        if (dao.getById(id) == null){
            throw new RecordNotFoundException("Record with id = " + id + " was not found.");
        }
        dao.remove(id);
    }

    private boolean isNameValid(String name){
        return name.matches("[a-zA-Zа-яА-Я\" -]{1,50}");
    }

    private boolean isFullNameValid(String fullName){
        return fullName.matches("[a-zA-Zа-яА-Я\" -]{1,200}");
    }

    private boolean isInnValid(String inn){
        return inn.matches("[0-9]{10}");
    }

    private boolean isKppValid(String kpp){
        return kpp.matches("[0-9]{9}");
    }

    private boolean isAddressValid(String address){
        return address.matches("[a-zA-Zа-яА-Я0-9 ,./-]{5,100}");
    }

    private boolean isPhoneValid(String phone){
        return phone.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
    }

    private void validateFilter(OrganizationListFilter filter){
        if (filter.name == null){
            throw new WrongRequestException("Field \"name\" is null.");
        }
    }

    private void validateView(OrganizationView view){
        StringBuilder messageBuilder = new StringBuilder("");
        if (view.id == null){
            messageBuilder.append("Field \"id\" is null. ");
        }
        if (view.name == null || !isNameValid(view.name)){
            messageBuilder.append("Field \"name\" is null or invalid. ");
        }
        if (view.fullName == null || !isFullNameValid(view.fullName)){
            messageBuilder.append("Field \"fullName\" is null or invalid. ");
        }
        if (view.inn == null || !isInnValid(view.inn)){
            messageBuilder.append("Field \"inn\" is null or invalid. ");
        }
        if (view.kpp == null || !isKppValid(view.kpp)){
            messageBuilder.append("Field \"kpp\" is null or invalid. ");
        }
        if (view.address == null || !isAddressValid(view.address)){
            messageBuilder.append("Field \"address\" is null or invalid. ");
        }
        if (view.phone != null && !isPhoneValid(view.phone)){
            messageBuilder.append("Field \"phone\" is invalid. ");
        }
        if (messageBuilder.length() > 0){
            throw new WrongRequestException(messageBuilder.toString().trim());
        }
        if (dao.getById(view.id) == null){
            throw new RecordNotFoundException("Record with id = " + view.id + " was not found on Organization.");
        }
    }

    private void validateToSaveView(OrganizationToSave view){
        StringBuilder messageBuilder = new StringBuilder("");
        if (view.name == null || !isNameValid(view.name)){
            messageBuilder.append("Field \"name\" is null or invalid. ");
        }
        if (view.fullName == null || !isFullNameValid(view.fullName)){
            messageBuilder.append("Field \"fullName\" is null or invalid. ");
        }
        if (view.inn == null || !isInnValid(view.inn)){
            messageBuilder.append("Field \"inn\" is null or invalid. ");
        }
        if (view.kpp == null || !isKppValid(view.kpp)){
            messageBuilder.append("Field \"kpp\" is null or invalid. ");
        }
        if (view.address == null || !isAddressValid(view.address)){
            messageBuilder.append("Field \"address\" is null or invalid. ");
        }
        if (view.phone != null && !isPhoneValid(view.phone)){
            messageBuilder.append("Field \"phone\" is invalid. ");
        }
        if (view.isActive == null){
            messageBuilder.append("Field \"isActive\" is null. ");
        }
        if (messageBuilder.length() > 0){
            throw new WrongRequestException(messageBuilder.toString().trim());
        }
    }

    private Function<Organization, OrganizationView> mapOrganization(){
        return o -> {
            OrganizationView view = new OrganizationView();
            view.id = o.getId();
            view.name = o.getName();
            view.fullName = o.getFullName();
            view.inn = o.getInn();
            view.kpp = o.getKpp();
            view.address = o.getAddress();
            view.phone = o.getPhone();
            view.isActive = o.getActive();
            return view;
        };
    }
}
