package ru.bellintegrator.practice.user.view;

import java.util.Date;

public class UserToSave {

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
     * Код страны, гражданином которой является сотрудник
     */
    public String citizenshipCode;

    /**
     * Прошел ли сотрудник идентификацию
     */
    public Boolean isIdentified;

    @Override
    public String toString(){
        return "{ firstName: \"" + firstName +
                "\"; lastName: \"" + lastName +
                "\"; middleName: \"" + middleName +
                "\"; officeId: " + officeId +
                "; position: \"" + position +
                "\"; phone: \"" + phone +
                "\"; docCode: " + docCode +
                "; docName: \"" + docName +
                "\"; docNumber: \"" + docNumber +
                "\"; docDate: " + docDate +
                "; citizenshipCode: " + citizenshipCode +
                "\"; isIdentified: " + isIdentified + "} ";
    }
}
