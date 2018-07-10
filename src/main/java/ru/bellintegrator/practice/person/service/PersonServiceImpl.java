package ru.bellintegrator.practice.person.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.person.dao.PersonDao;
import ru.bellintegrator.practice.person.model.Person;
import ru.bellintegrator.practice.person.view.PersonView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class PersonServiceImpl implements PersonService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final PersonDao dao;

    @Autowired
    public PersonServiceImpl(PersonDao dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(PersonView view) {
        Person person = new Person(view.name, view.age);
        dao.save(person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonView> persons() {
        List<Person> all = dao.all();

        return all.stream()
                .map(mapPerson())
                .collect(Collectors.toList());
    }

    private Function<Person, PersonView> mapPerson() {
        return p -> {
            PersonView view = new PersonView();
            view.id = String.valueOf(p.getId());
            view.name = p.getName();
            view.age = p.getAge();

            log.debug(view.toString());

            return view;
        };
    }
}
