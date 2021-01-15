package com.home.dogs.rest;

import com.home.dogs.domain.Person;
import com.home.dogs.dto.PersonDto;
import com.home.dogs.mapper.PersonMapper;
import com.home.dogs.mapper.PersonMapperImpl;
import com.home.dogs.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("people")
public class PersonController {

    private final PersonService personService;

    private final PersonMapper personMapper = new PersonMapperImpl();

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll() {
        List<Person> personList = personService.getAll();
        return new ResponseEntity<>(personMapper.toDto(personList), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> get(@PathVariable Long id) {
        Person person = personService.get(id);
        return new ResponseEntity<>(personMapper.toDto(person), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody PersonDto request) {
        personService.create(personMapper.toEntity(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody String name) {
        personService.update(id, name);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }
}
