package com.home.dogs.repository;

import com.home.dogs.domain.Dog;
import com.home.dogs.domain.Person;
import com.home.dogs.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DogRepository {


    private List<Dog> dogList = new ArrayList<>();

    public DogRepository() {
        initDogList();
    }

    public List<Dog> getAll() {
        return dogList;
    }

    public Dog get(Long id) {
        return dogList.stream()
                .filter(person -> person.getId().equals(id))
                .findAny()
                .orElseThrow(()-> new EntityNotFoundException(String.format("Dog with identifier %s could not be found" , id)));
    }

    public Dog save(Dog dog) {
        dogList.add(dog);
        return dog;
    }

    public Dog update(Dog request) {
        Optional<Dog> dogUpdated = Optional.ofNullable(get(request.getId()));
        if(dogUpdated.isPresent()) {
            dogList.remove(dogUpdated.get());
            dogList.add(request);
            return request;
        }
        return null;
    }

    public String delete(Long id ) {
        Optional<Dog> dogDeleted = Optional.ofNullable(get(id));
        if (dogDeleted.isPresent()) {
            dogList.remove(dogDeleted.get());
            return "Removed!";
        }
        return "Dog not found!";
    }

    private void initDogList() {
        dogList.add(Dog.builder()
                .id(1L)
                .name("Tomas")
                .age(2)
                .breed("beagle")
                .color("many")
                .hasOwner(true)
                .hasCode(false)
                .code(null)
                .owner(Person.builder().id(1L).name("Mike").age(26).city("Bucharest").build())
                .build());
        dogList.add(Dog.builder()
                .id(2L)
                .name("Whisky")
                .age(5)
                .breed("bichon")
                .color("white")
                .hasCode(true)
                .code("abcd")
                .hasOwner(true)
                .owner(Person.builder().id(2L).name("Marcel").age(30).city("Iasi").build())
                .build());
        dogList.add(Dog.builder()
                .id(3L)
                .name("Rita")
                .age(1)
                .breed("wolf")
                .color("brown")
                .hasCode(true)
                .code("abcd")
                .hasOwner(true)
                .owner(Person.builder().id(3L).name("Maria").age(15).city("Cluj").build())
                .build());
    }
}
