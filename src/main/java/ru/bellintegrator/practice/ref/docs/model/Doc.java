package ru.bellintegrator.practice.ref.docs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Doc")
public class Doc {

    /**
     * Код документа
     */
    @Id
    @Column(name = "code", nullable = false)
    private Integer code;

    /**
     * Название документа
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Конструктор для Hibernate
     */
    public Doc(){

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
