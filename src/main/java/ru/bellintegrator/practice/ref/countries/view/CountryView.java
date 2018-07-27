package ru.bellintegrator.practice.ref.countries.view;

public class CountryView {

    /**
     * Название страны
     */
    public String name;

    /**
     * Код страны
     */
    public Integer code;

    @Override
    public String toString(){
        return "{ name: \"" + name +
                "\"; code: " + code + " }";
    }
}
