package ru.bellintegrator.practice.office.view;

public class OfficeListFilter {

    /**
     * Идентификатор организации, к которой относится офис
     */
    public Long orgId;

    /**
     * Название организации, к которой относится офис
     */
    public String name;

    /**
     * Телефон организации, к которой относится офис
     */
    public String phone;

    /**
     * Действует ли организация, к которой относится офис
     */
    public Boolean isActive;

    @Override
    public String toString(){
        return "{ orgId: " + orgId +
                "; name: \"" + name +
                "\"; phone: \"" + phone +
                "\"; isActive: " + isActive + " }";
    }

}
