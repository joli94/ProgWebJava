package com.home.dogs.service;

import com.home.dogs.domain.Dog;
import com.home.dogs.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {
    @Autowired
    private final DogRepository dogRepository;

    @Autowired
    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public List<Dog> getAll() {
        return dogRepository.getAll();
    }

    public Dog get(Long id) {
        return dogRepository.get(id);

    }

    public Dog create(Dog request) {
        request.setId(Long.valueOf(dogRepository.getAll().size()) + 1);
        Dog savedDog = dogRepository.save(request);
        return savedDog;
    }

    public Dog update(Dog request) {
        Dog updatedDog = dogRepository.update(request);
        if (updatedDog != null) {
            return updatedDog;
        }
        return null;
    }

    public String delete(Long id) {
        return dogRepository.delete(id);
    }
}
