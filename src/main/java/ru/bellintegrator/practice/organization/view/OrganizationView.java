package ru.bellintegrator.practice.organization.view;

public class OrganizationView {

    /**
     * Идентификатор
     */
    public Long id;

    /**
     * Название организации
     */
    public String name;

    /**
     * Полное наименование организации
     */
    public String fullname;

    /**
     * ИНН организации
     */
    public String inn;

    /**
     * Код причины постановки на учет
     */
    public String kpp;

    /**
     * Юридический адрес организации
     */
    public String address;

    /**
     * Телефон организации
     */
    public String phone;

    /**
     * Флаг, показывающий, действует ли организация
     */
    public Boolean isActive;

    @Override
    public String toString(){
        return "{ id: " + id +
                "; name: \"" + name +
                "\"; fullname: \"" + fullname +
                "\"; inn: \"" + inn +
                "\"; kpp: \"" + kpp +
                "\"; address: \"" + address +
                "\"; phone: \"" + phone +
                "\"; isActive: " + isActive + " }";
    }
}
