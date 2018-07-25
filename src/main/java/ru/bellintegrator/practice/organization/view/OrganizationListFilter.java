package ru.bellintegrator.practice.organization.view;

/**
 * Класс объекта фильтрации списка организаций
 */
public class OrganizationListFilter {

    /**
     * Название организации
     */
    public String name;

    /**
     * ИНН организации
     */
    public String inn;

    /**
     * Действующая организация
     */
    public Boolean isActive;

    @Override
    public String toString(){
        return "{ name: \"" + name +
                "\"; inn: \"" + inn +
                "\"; isActive: " + isActive + " }";
    }

}
