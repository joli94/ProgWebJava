package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.dto.ConsumedSubstanceDto;
import com.proiect.chemdb.dto.SubstanceDto;
import com.proiect.chemdb.mapper.SubstanceMapper;
import com.proiect.chemdb.service.SubstanceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("substances")
@Api(
   value = "substances",
   tags = "Substances existent in the lab"
)
public class SubstanceController {
    private final SubstanceService service;
    private final SubstanceMapper mapper;

    @Autowired
    public SubstanceController(SubstanceService service, SubstanceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a Substance",
            notes = "Gets the Substance having the id value the one received in the link")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The substance was successfully found in the database"),
            @ApiResponse(code = 404, message = "There is no substance in the database having this value for id")
    })
    public ResponseEntity<SubstanceDto> getById (@PathVariable @ApiParam(name="substance", value="Substance id", required = true) Long id) {
        Substance response = service.getById(id);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @GetMapping(path= "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a Substance",
            notes = "Gets the Substance using as query the link")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The substance was successfully found in the database"),
            @ApiResponse(code = 404, message = "There is no substance in the database having this property")
    })
    public ResponseEntity<List<SubstanceDto>> getByQuery(@PathVariable @ApiParam(name = "query", value="CAS, molecular formula or full name", required = true) String query) {
        List<Substance> response = service.getByQuery(query);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the Substances of an User",
            notes = "Gets the Substances which belong to the User having the id value the one received in the link")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "he list was successfully obtained"),
            @ApiResponse(code = 404, message = "There is no substance in the database belonging to this user")
    })
    public ResponseEntity<List<SubstanceDto>> getByUser(@PathVariable @ApiParam(name="id", value="User id", required = true) Long id) {
        List<Substance> response = service.getByUser(id);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new Substance",
            notes = "Creates a new Substance based on the information received in the request")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "The Substance was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<SubstanceDto> create (@Valid @RequestBody @ApiParam(name="substance", value="Substance details", required = true) SubstanceDto request) {
        Substance substance = service.create(mapper.toEntity(request));
        return new ResponseEntity<>(mapper.toDto(substance), HttpStatus.CREATED);
    }
}
