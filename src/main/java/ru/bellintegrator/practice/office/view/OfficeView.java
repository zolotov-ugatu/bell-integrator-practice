package ru.bellintegrator.practice.office.view;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficeView {

    /**
     * Идентификатор
     */
    public Long id;

    /**
     * Наименование офиса
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
     * Номер телефона офиса
     */
    public String phone;

    /**
     * Действующий ли офис
     */
    public Boolean isActive;

    @Override
    public String toString(){
        return "{ id:" + id +
                "; name: \"" + name +
                "\"; orgId: " + orgId +
                "; address: \"" + address +
                "\"; phone: \"" + phone +
                "\"; isActive: " + isActive + " }";
    }

}
