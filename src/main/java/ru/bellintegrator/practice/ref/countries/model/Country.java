package ru.bellintegrator.practice.ref.countries.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Country")
public class Country {

    /**
     * Код страны
     */
    @Id
    @Column(name = "code", nullable = false)
    private Integer code;

    /**
     * Название страны
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Конструктор для Hibernate
     */
    public Country(){

    }

    /* Геттеры и сеттеры */

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}