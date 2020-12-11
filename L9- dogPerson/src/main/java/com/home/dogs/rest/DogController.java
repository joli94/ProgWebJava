package com.home.dogs.rest;

import com.home.dogs.domain.Dog;
import com.home.dogs.dto.DogDto;
import com.home.dogs.mapper.DogMapper;
import com.home.dogs.mapper.DogMapperImpl;
import com.home.dogs.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dogs")
public class DogController {

    @Autowired
    private final DogService dogService;

    private final DogMapper dogMapper = new DogMapperImpl();

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public ResponseEntity<List<DogDto>> getAll() {
        List<Dog> dogList = dogService.getAll();
        return new ResponseEntity<>(dogMapper.toDto(dogList), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DogDto> get(@PathVariable Long id) {
        Dog dog = dogService.get(id);
        return new ResponseEntity<>(dogMapper.toDto(dog), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody DogDto request) {
        dogService.create(dogMapper.toEntity(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody String name) {
        dogService.update(id, name);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        dogService.delete(id);
    }
}

