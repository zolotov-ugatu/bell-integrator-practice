package ru.bellintegrator.practice.office.model;

import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.user.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Office")
public class Office {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * Поле для Hibernate
     */
    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * Название офиса
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Организация, к которой относится офис
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    /**
     * Адрес офиса
     */
    @Column(name = "address", length = 100, nullable = false)
    private String address;

    /**
     * Номер телефона офиса
     */
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    /**
     * Действующий ли офис
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /**
     * Список работников офиса
     */
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    /**
     * Конструктор для Hibernate
     */
    public Office(){

    }

    /**
     * Конструктор
     *
     * @param name название офиса
     * @param organization организация, к которой относится офис
     * @param address адрес офиса
     * @param phone номер телефона
     * @param isActive действует ли офис
     */
    public Office(String name, Organization organization, String address, String phone, Boolean isActive){
        this.name = name;
        this.organization = organization;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
     * Возвращает список работников офиса
     *
     * @return список работников офиса
     */
    public List<User> getUsers(){
        if (users == null){
            return new ArrayList<>();
        }
        return users;
    }

    /**
     * Добавляет работника офиса
     *
     * @param user новый работник
     */
    public void addUser(User user){
        getUsers().add(user);
        user.setOffice(this);
    }

    /**
     * Удаляет работника офиса
     *
     * @param user работник для удаления
     */
    public void removeUser(User user){
        getUsers().remove(user);
        user.setOffice(null);
    }
}
