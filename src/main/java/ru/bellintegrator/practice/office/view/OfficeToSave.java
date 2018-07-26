package ru.bellintegrator.practice.office.view;

public class OfficeToSave {

    /**
     * Наименование нового офиса
     */
    public String name;

    /**
     * Идентификатор организации, к которой относится офис
     */
    public Long orgId;

    /**
     * Адрес офиса
     */
    public String address;

    /**
     * Телефон офиса
     */
    public String phone;

    /**
     * Действующий ли офис
     */
    public Boolean isActive;

    @Override
    public String toString(){
        return "{ name: \"" + name +
                "\"; orgId: " + orgId +
                "; address: \"" + address +
                "\"; phone: \"" + phone +
                "\"; isActive: " + isActive + " }";
    }
}
