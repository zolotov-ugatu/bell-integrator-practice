package ru.bellintegrator.practice.person.model;

import ru.bellintegrator.practice.house.model.House;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Set;

/**
 * Человек
 */
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Иия
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String name;

    /**
     * Возраст
     * т.к. поле примитивного типа, оно не может быть nullable
     */
    @Column(name = "age", nullable = false)
    private int age;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "Person_House",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    private Set<House> houses;

    /**
     * Конструктор для hibernate
     */
    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<House> getHouses() {
        if (houses == null) {
            houses = new HashSet<>();
        }
        return houses;
    }

    public void addHouse(House house) {
        getHouses().add(house);
        house.getPersons().add(this);
    }

    public void removeHouse(House house) {
        getHouses().remove(house);
        house.getPersons().remove(this);
    }

}
