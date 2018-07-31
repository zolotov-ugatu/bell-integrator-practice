package ru.bellintegrator.practice.organization.view;

/**
 * Класс объекта с данными для сохранения новой организации
 */
public class OrganizationToSave {

    /**
     * Название организации
     */
    public String name;

    /**
     * Полное наименование организации
     */
    public String fullName;

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
        return "{ name: " + name +
                "; fullName: \"" + fullName +
                "\"; kpp: \"" + kpp +
                "\"; address: \"" + address +
                "\"; phone: \"" + phone +
                "\"; isActive: " + isActive + " }";
    }
}
