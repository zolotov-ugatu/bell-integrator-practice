package ru.bellintegrator.practice.organization.model;

import ru.bellintegrator.practice.office.model.Office;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Organization")
public class Organization {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * Поле Hibernate
     */
    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * Название организации
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Полное наименование организации
     */
    @Column(name = "full_name", length = 200, nullable = false)
    private String fullName;

    /**
     * Идентификационный номер налогоплательщика
     */
    @Column(name = "inn", length = 10, nullable = false)
    private String inn;

    /**
     * Код причины постановки на учет
     */
    @Column(name = "kpp", length = 9, nullable = false)
    private String kpp;

    /**
     * Юридический адрес организации
     */
    @Column(name = "address", length = 100, nullable = false)
    private String address;

    /**
     * Номер телефона
     */
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    /**
     * Действующая ли организация
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /**
     * Список офисов организации
     */
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Office> offices;

    /**
     * Конструктор для Hibernate
     */
    public Organization(){

    }

    /**
     * Конструктор
     *
     * @param name название организации
     * @param fullName полное наименование организации
     * @param inn идентификационный номер налогоплательщика
     * @param kpp код причины постановки на учет
     * @param address юридический адрес организации
     * @param phone номер телефона
     * @param isActive действующая ли организация
     */
    public Organization (String name, String fullName, String inn, String kpp, String address, String phone, Boolean isActive){
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    /* Геттеры и сеттеры */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    /**
     * Получение списка офисов организации
     *
     * @return список офисов организации
     */
    public List<Office> getOffices() {
        if (offices == null){
            return new ArrayList<>();
        }
        return offices;
    }

    /**
     * Добавляет офис организации
     *
     * @param office новый офис организации
     */
    public void addOffice(Office office) {
        getOffices().add(office);
        office.setOrganization(this);
    }

    /**
     * Удаляет офис организации
     *
     * @param office офис для удаления
     */
    public void removeOffice(Office office){
        getOffices().remove(office);
    }
}
