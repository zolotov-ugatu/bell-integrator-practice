package ru.bellintegrator.practice.user.model;

import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.ref.countries.model.Country;
import ru.bellintegrator.practice.ref.docs.model.Doc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Date;

@Entity
@Table(name = "User")
public class User {

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
     * Имя
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "middle_name", length = 50)
    private String middleName;

    /**
     * Офис, в котором работает пользователь
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    /**
     * Должность
     */
    @Column(name = "position", length = 50, nullable = false)
    private String position;

    /**
     * Номер телефона
     */
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    /**
     * Документ, удостоверяющий личнось работника
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    private Doc doc;

    /**
     * Номер документа, удостоверяющего личность работника
     */
    @Column(name = "doc_number", length = 20, nullable = false)
    private String docNumber;

    /**
     * Дата выдачи документа, удостоверяющего личность
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "doc_date", nullable = false)
    private Date docDate;

    /**
     * Страна, гражданином которой является работник
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenship_id", nullable = false)
    private Country country;

    /**
     * Авторизован ли работник
     */
    @Column(name = "is_identified", nullable = false)
    private Boolean isIdentified;

    /**
     * Конструктор для Hibernate
     */
    public User(){

    }

    /**
     * Конструктор
     *
     * @param firstName имя
     * @param lastName фамилия
     * @param middleName отчество
     * @param office офис
     * @param position должность
     * @param phone номер телефона
     * @param doc документ, удостоверяющий личность
     * @param docNumber номер документа, удостоверяющего личность
     * @param docDate дата выдачи документа
     * @param country гражданство
     * @param isIdentified авторизованный работник
     */
    public User(String firstName, String lastName, String middleName, Office office, String position,
                String phone, Doc doc, String docNumber, Date docDate, Country country, Boolean isIdentified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.office = office;
        this.position = position;
        this.phone = phone;
        this.doc = doc;
        this.docNumber = docNumber;
        this.docDate = docDate;
        this.country = country;
        this.isIdentified = isIdentified;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Office getOffice() {
        if (office == null){
            office = new Office();
        }
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Doc getDoc() {
        if (doc == null){
            doc = new Doc();
        }
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Country getCountry() {
        if (country == null){
            country = new Country();
        }
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }
}
