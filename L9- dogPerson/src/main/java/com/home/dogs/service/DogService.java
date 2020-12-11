package com.home.dogs.service;

import com.home.dogs.domain.Dog;
import com.home.dogs.domain.Person;
import com.home.dogs.exceptions.EntityNotFoundException;
import com.home.dogs.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {
    @Autowired
    private DogRepository dogRepository;

    public List<Dog> getAll() {
        return dogRepository.findAll();
    }

    public Dog get(Long id) {
        return dogRepository.findBy(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No dog present with id %s", id.toString())));
    }

    public void create(Dog request) {
        dogRepository.save(request);
    }

    public void update(Long id, String name) {
        dogRepository.update(id, name);
    }

    public void delete(Long id) {
        dogRepository.delete(id);
    }
}
