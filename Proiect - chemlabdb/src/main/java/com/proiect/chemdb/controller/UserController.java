package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.dto.SubstanceDto;
import com.proiect.chemdb.dto.UserDto;
import com.proiect.chemdb.exception.BadRequestException;
import com.proiect.chemdb.mapper.UserMapper;
import com.proiect.chemdb.service.SubstanceService;
import com.proiect.chemdb.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@Api(value = "/users",
        tags = "Users")
public class UserController {
    private final UserMapper mapper;
    private final UserService service;

    @Autowired
    public UserController(UserMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get an User",
            notes = "Gets the User having the id value the one received in the link")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The user was successfully found in the database"),
            @ApiResponse(code = 404, message = "There is no user in the database having this value for id")
    })
    public ResponseEntity<UserDto> get (@PathVariable @ApiParam(name="user", value="User id", required = true) Long id) {
        User response = service.getBy(id);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new User",
                notes = "Creates a new User based on the information received in the request")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "The user was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<UserDto> create (@Valid @RequestBody @ApiParam(name="user", value="User details", required = true) UserDto request) {
        User user = service.create(mapper.toEntity(request));
        return new ResponseEntity<>(mapper.toDto(user), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update the User",
            notes = "Updates the User having the id equal to the one received in link with the information received in the request")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The user was successfully updated based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 404, message = "There is no user in the database having this value for id ")
    })
    public ResponseEntity<UserDto> update (@PathVariable Long id, @Valid @RequestBody UserDto request) {
        if(id != request.getId()){
            throw new BadRequestException("The path variable doesn't match the request body id!");
        }

        User user = service.update(mapper.toEntity(request));
        return new ResponseEntity<>(mapper.toDto(user), HttpStatus.OK);
    }
}
