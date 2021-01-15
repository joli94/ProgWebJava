package com.home.dogs.repository;

import com.home.dogs.domain.Dog;
import com.home.dogs.domain.Person;
import com.home.dogs.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {
    private final List<Person> personList = new ArrayList<>();

    public List<Person> getAll() {
        return personList;
    }

    public Person get(Long id) {
        return personList.stream()
                .filter(person -> person.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person with identifier %s could not be found", id)));
    }

    public Person save(Person person) {
        personList.add(person);
        return person;
    }

    public Person update(Person request) {
        Optional<Person> personUpdated = Optional.ofNullable(get(request.getId()));
        if (personUpdated.isPresent()) {
            personList.remove(personUpdated.get());
            personList.add(request);
            return request;
        }
        return null;
    }

    public String delete(Long id) {
        Optional<Person> personDeleted = Optional.ofNullable(get(id));
        if (personDeleted.isPresent()) {
            personList.remove(personDeleted.get());
            return "Removed!";
        }
        return "Dog not found!";
    }

    @PostConstruct
    public void init() {
        personList.add(Person.builder().id(1L).name("Mike").age(26).city("Bucharest").build());
        personList.add(Person.builder().id(2L).name("Marcel").age(30).city("Iasi").build());
        personList.add(Person.builder().id(3L).name("Maria").age(15).city("Cluj").build());

    }
}
