package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.dto.BuySubstanceDto;
import com.proiect.chemdb.mapper.BuySubstanceMapper;
import com.proiect.chemdb.service.BuySubstanceService;
import io.swagger.annotations.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("buy")
@Api(
        value = "/buy",
        tags = "Buy new substances"
)
public class BuySubstanceController {
    private final BuySubstanceService service;
    private final BuySubstanceMapper mapper;

    @Autowired
    public BuySubstanceController(BuySubstanceService service, BuySubstanceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all substances",
            notes = "Gets the list with all substances need to be purchased")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The list was successfully obtained"),
            @ApiResponse(code = 404, message = "There is no substance to be purchased")
    })
    public ResponseEntity<List<BuySubstanceDto>> getAll() {
        List<BuySubstance> response = service.getAll();
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get substances from user",
            notes = "Gets the list with all substances need to be purchased added by the user having the id given in the link")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The list was successfully obtained"),
            @ApiResponse(code = 404, message = "There is no substance to be purchased from this user")
    })
    public ResponseEntity<List<BuySubstanceDto>> getByUser(@PathVariable @ApiParam(name="userId", value = "User id from the database") Long userId) {
        List<BuySubstance> response = service.getByUser(userId);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a substance",
            notes = "Adds a new substances into the purchases list based on the information received in the request")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "The substance was successfully added based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuySubstanceDto> create (@Valid @RequestBody @ApiParam(name="substance", value="Substance details", required = true) BuySubstanceDto request) {
        BuySubstance buySubstance= service.create(mapper.toEntity(request));
        return new ResponseEntity<>(mapper.toDto(buySubstance), HttpStatus.CREATED);
    }
}
