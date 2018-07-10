package ru.bellintegrator.practice.house.model;

import ru.bellintegrator.practice.person.model.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Set;

/**
 * Дом
 */
@Entity(name = "House")
public class House {

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
     * Адрес
     */
    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @ManyToMany(mappedBy = "houses")
    private Set<Person> persons;

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Person> getPersons() {
        if (persons == null) {
            persons = new HashSet<>();
        }
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}