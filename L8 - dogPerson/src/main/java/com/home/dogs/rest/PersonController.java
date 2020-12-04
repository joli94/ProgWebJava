package com.home.dogs.rest;

import com.home.dogs.dto.PersonDto;
import com.home.dogs.mapper.PersonMapper;
import com.home.dogs.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("persons")
public class PersonController {

    @Autowired
    private  PersonService personService;

    @Autowired
    private PersonMapper personMapper;


    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDto>> getAll() {
        return new ResponseEntity<>(personService.getAll().stream()
                .map(person -> personMapper.toDto(person))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(personMapper.toDto(personService.get(id)), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> create(@Valid @RequestBody PersonDto request) {
        return new ResponseEntity<>(personMapper.toDto(personService.create(personMapper.toEntity(request))), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> update(@RequestBody PersonDto request) {
        return new ResponseEntity<>(personMapper.toDto(personService.update(personMapper.toEntity(request))), personService.update(personMapper.toEntity(request))!= null ? HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String delete(@PathVariable Long id) {
        return personService.delete(id);
    }
}
