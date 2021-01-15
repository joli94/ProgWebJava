package com.home.dogs.rest;

import com.home.dogs.dto.DogDto;
import com.home.dogs.mapper.DogMapper;
import com.home.dogs.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dogs")
public class DogController {

    @Autowired
    private final DogService dogService;
    @Autowired
    private final DogMapper dogMapper;

    @Autowired
    public DogController(DogService dogService, DogMapper dogMapper) {
        this.dogService = dogService;
        this.dogMapper = dogMapper;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DogDto>> getAll() {
        return new ResponseEntity<>(dogService.getAll().stream()
                .map(dog -> dogMapper.toDto(dog))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DogDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(dogMapper.toDto(dogService.get(id)), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DogDto> create(@Valid @RequestBody DogDto request) {
        return new ResponseEntity<>(dogMapper.toDto(dogService.create(dogMapper.toEntity(request))), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DogDto> update(@RequestBody DogDto request) {
        return new ResponseEntity<>(dogMapper.toDto(dogService.update(dogMapper.toEntity(request))), dogService.update(dogMapper.toEntity(request)) != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String delete(@PathVariable Long id) {
        return dogService.delete(id);
    }
}

