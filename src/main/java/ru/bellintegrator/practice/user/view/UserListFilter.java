package ru.bellintegrator.practice.user.view;

public class UserListFilter {

    /**
     * Идентификатор офиса, к которому относится сотрудник
     */
    public Long officeId;

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
     * Должность
     */
    public String position;

    /**
     * Код документа, удостоверяющего личность
     */
    public Integer docCode;

    /**
     * Код страны, гражданином которой является сотрудник
     */
    public Integer citizenshipCode;

    @Override
    public String toString(){
        return "{ officeId: " + officeId +
                "; firstName: \"" + firstName +
                "\"; lastName: \"" + lastName +
                "\"; middleName: \"" + middleName +
                "\"; position: \"" + position +
                "\"; docCode: " + docCode +
                "; citizenshipCode: " + citizenshipCode + "} ";
    }

}
