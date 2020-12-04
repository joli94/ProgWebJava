package com.home.dogs.service;

import com.home.dogs.domain.Dog;
import com.home.dogs.domain.Person;
import com.home.dogs.exceptions.BadRequestException;
import com.home.dogs.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public List<Person> getAll() {
        return personRepository.getAll();
    }

    public Person get(Long id) {
        return personRepository.get(id);
    }

    public Person create(Person request) {
        validateRequest(request);
        request.setId(Long.valueOf(personRepository.getAll().size()) +1);
        Person savedPerson =  personRepository.save(request);
        return savedPerson;
    }

    public Person update(Person request) {
        Person updatedPerson = personRepository.update(request);
        if(updatedPerson != null) {
            return updatedPerson;
        }
        return null;
    }

    public String delete(Long id) {
        return personRepository.delete(id);
    }

    private void validateRequest(Person request) {
        if (personRepository.getAll().stream()
                .anyMatch(person-> person.getName().equals(request.getName()))) {
            throw new BadRequestException("Duplicate person!");
        }
    }
}