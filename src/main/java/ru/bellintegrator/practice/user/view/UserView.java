package ru.bellintegrator.practice.user.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Класс представления пользователя
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserView {

    /**
     * Идентификатор
     */
    public Long id;

    /**
     * Имя
     */
    public String firstName;

    /**
     * Фамилия
     */
    public String lastName;

    /**
     * Отчество
     */
    public String middleName;

    /**
     * Идентификатор офиса, к которому относится сотрудник
     */
    public Long officeId;

    /**
     * Должность
     */
    public String position;

    /**
     * Номер телефона
     */
    public String phone;

    /**
     * Код документа, удостоверяющего личность
     */
    public Integer docCode;

    /**
     * Название документа, удостоверяющего личность
     */
    public String docName;

    /**
     * Номер документа, удостоверяющего личность
     */
    public String docNumber;

    /**
     * Дата выдачи документа, удостоверяющего личность
     */
    public Date docDate;

    /**
     * Страна, гражданином которой является сотрудник
     */
    public String citizenshipName;

    /**
     * Код страны, гражданином которой является сотрудник
     */
    public Integer citizenshipCode;

    /**
     * Прошел ли сотрудник идентификацию
     */
    public Boolean isIdentified;

    @Override
    public String toString(){
        return "{ id: " + id +
                "; firstName: \"" + firstName +
                "\"; lastName: \"" + lastName +
                "\"; middleName: \"" + middleName +
                "\"; officeId: " + officeId +
                "; position: \"" + position +
                "\"; phone: \"" + phone +
                "\"; docCode: " + docCode +
                "; docName: \"" + docName +
                "\"; docNumber: \"" + docNumber +
                "\"; docDate: " + docDate +
                "; citizenshipName: \"" + citizenshipName +
                "\"; citizenshipCode: " + citizenshipCode +
                "\"; isIdentified: " + isIdentified + "} ";
    }
}
