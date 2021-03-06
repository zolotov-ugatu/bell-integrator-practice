package ru.bellintegrator.practice.office.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.bellintegrator.practice.exception.RecordNotFoundException;
import ru.bellintegrator.practice.exception.WrongRequestException;
import ru.bellintegrator.practice.office.dao.OfficeDao;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.view.OfficeListFilter;
import ru.bellintegrator.practice.office.view.OfficeToSave;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.organization.dao.OrganizationDao;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OfficeDao officeDao;
    private final OrganizationDao organizationDao;

    /**
     * Конструктор
     *
     * @param officeDao DAO-слой для работы с офисами
     * @param organizationDao DAO-слой для работы с организациями
     */
    @Autowired
    public OfficeServiceImpl(OfficeDao officeDao, OrganizationDao organizationDao){
        this.officeDao = officeDao;
        this.organizationDao = organizationDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OfficeView> list(OfficeListFilter filterView) {
        validateFilter(filterView);
        Office filter = new Office();
        filter.setName(filterView.name);
        filter.getOrganization().setId(filterView.orgId);
        filter.setPhone(filterView.phone);
        filter.setActive(filterView.isActive);
        List<Office> list = officeDao.list(filter);
        return list.stream().map(mapOffice()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OfficeView getById(Long officeId) {
        if (officeId == null){
            throw new WrongRequestException("\"id\" is null.");
        }
        Office office = officeDao.getById(officeId);
        if (office == null){
            throw new RecordNotFoundException("Record with id = " + officeId + " was not found in Office.");
        }
        OfficeView view = new OfficeView();
        view.id = office.getId();
        view.name = office.getName();
        view.orgId = office.getOrganization().getId();
        view.address = office.getAddress();
        view.phone = office.getPhone();
        view.isActive = office.getActive();
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(OfficeView view) {
        validateToUpdateView(view);
        Office office = new Office();
        office.setId(view.id);
        office.setName(view.name);
        office.setAddress(view.address);
        if (view.phone != null){
            office.setPhone(StringUtils.isEmpty(view.phone) ? null : view.phone);
        }
        else {
            office.setPhone(officeDao.getById(view.id).getPhone());
        }
        office.setActive(view.isActive != null ? view.isActive : officeDao.getById(view.id).getActive());
        officeDao.update(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(OfficeToSave officeToSave) {
        validateToSaveView(officeToSave);
        Office office = new Office();
        office.setName(officeToSave.name);
        office.setAddress(officeToSave.address);
        office.setPhone(officeToSave.phone);
        office.setActive(officeToSave.isActive);
        organizationDao.getById(officeToSave.orgId).addOffice(office);
        officeDao.save(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void remove(Long id){
        if (id == null){
            throw new WrongRequestException("Id is null.");
        }
        if (officeDao.getById(id) == null){
            throw new RecordNotFoundException("Record with id = " + id + " was not found in Office.");
        }
        organizationDao.getById(officeDao.getById(id).getOrganization().getId()).removeOffice(officeDao.getById(id));
        officeDao.remove(id);
    }

    private boolean isNameValid(String name){
        return name.matches("[a-zA-Zа-яА-Я\" .-]{1,50}");
    }

    private boolean isAddressValid(String address){
        return address.matches("[a-zA-Zа-яА-Я0-9 ,./-]{5,100}");
    }

    private boolean isPhoneValid(String phone){
        return phone.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
    }

    private void validateFilter(OfficeListFilter filter){
        if (filter.orgId == null){
            throw new WrongRequestException("Field \"orgId\" is null.");
        }
    }

    private void validateToUpdateView(OfficeView view){
        StringBuilder messageBuilder = new StringBuilder("");
        if (view.id == null){
            messageBuilder.append("Field \"id\" is null. ");
        }
        if (view.name == null || !isNameValid(view.name)){
            messageBuilder.append("Field \"name\" is null or invalid. ");
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
        if (officeDao.getById(view.id) == null){
            throw new RecordNotFoundException("Record with id = " + view.id + " was not found in Office.");
        }
    }

    private void validateToSaveView(OfficeToSave view){
        StringBuilder messageBuilder = new StringBuilder("");
        if (view.name == null || !isNameValid(view.name)){
            messageBuilder.append("Field \"name\" is null or invalid. ");
        }
        if (view.orgId == null){
            messageBuilder.append("Field \"orgId\" is null. ");
        }
        if (view.address == null || !isAddressValid(view.address)){
            messageBuilder.append("Field \"address\" is null or invalid. ");
        }
        if (view.phone != null && !isPhoneValid(view.phone)){
            messageBuilder.append("Field \"phone\" is null or invalid. ");
        }
        if (view.isActive == null){
            messageBuilder.append("Field \"isActive\" is null.");
        }
        if (messageBuilder.length() > 0){
            throw new WrongRequestException(messageBuilder.toString().trim());
        }
        if (organizationDao.getById(view.orgId) == null){
            throw new RecordNotFoundException("Record with id = " + view.orgId + " was not found in Organization.");
        }
    }

    private Function<Office, OfficeView> mapOffice(){
        return o -> {
            OfficeView view = new OfficeView();
            view.id = o.getId();
            view.name = o.getName();
            view.isActive = o.getActive();
            return view;
        };
    }
}
