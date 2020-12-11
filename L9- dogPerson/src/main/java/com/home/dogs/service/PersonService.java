package com.home.dogs.service;

import com.home.dogs.domain.Person;
import com.home.dogs.exceptions.EntityNotFoundException;
import com.home.dogs.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person get(Long id) {
        return personRepository.findBy(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No person present with id %s", id.toString())));
    }

    public void create(Person request) {
        personRepository.save(request);
    }

    public void update(Long id, String name) {
        personRepository.update(id, name);
    }

    public void delete(Long id) {
        personRepository.delete(id);
    }
}