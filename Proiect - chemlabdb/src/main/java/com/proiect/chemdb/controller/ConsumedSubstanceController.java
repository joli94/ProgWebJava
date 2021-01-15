package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.ConsumedSubstance;
import com.proiect.chemdb.dto.ConsumedSubstanceDto;
import com.proiect.chemdb.mapper.ConsumedSubstanceMapper;
import com.proiect.chemdb.service.ConsumedSubstanceService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("substances/consumed")
@Api(
        value = "/consumed",
        tags = "Log with all the consumed amounts for substances"
)
public class ConsumedSubstanceController {
    private final ConsumedSubstanceService service;
    private final ConsumedSubstanceMapper mapper;

    public ConsumedSubstanceController(ConsumedSubstanceService service, ConsumedSubstanceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all consumed substances",
            notes = "Gets the log with all consumed amounts from all the substances")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The list was successfully obtained"),
            @ApiResponse(code = 404, message = "No substance was used")
    })
    public ResponseEntity<List<ConsumedSubstanceDto>> getAll() {
        List<ConsumedSubstance> response = service.getAll();
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @GetMapping(path = "/substance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get consumed amounts for substance",
                 notes = "Gets the list with all the consumed amounts for the substance with the given id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The list was successfully obtained"),
            @ApiResponse(code = 404, message = "This substance was not used in any experiments")
    })
    public ResponseEntity<List<ConsumedSubstanceDto>> getBySubstance(@PathVariable @ApiParam(name="id", value = "Substance id", required = true) Long id) {
        List<ConsumedSubstance> response = service.getBySubstance(id);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get consumed amounts by user",
            notes = "Gets the list with all the consumed amounts by the user with the given id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "The list was successfully obtained"),
            @ApiResponse(code = 404, message = "This user has not performed any experiments")
    })
    public ResponseEntity<List<ConsumedSubstanceDto>> getByUser(@PathVariable @ApiParam(name="id", value = "User if id", required = true) Long id) {
        List<ConsumedSubstance> response = service.getByUser(id);
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a consumed substance",
            notes = "Adds a new usage of a substance based on the information received in the request")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "The usage was successfully added based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 404, message = "Some of the information provided are not present in the databaser")
    })
    public ResponseEntity<ConsumedSubstanceDto> add(@Valid @RequestBody @ApiParam(name="amount", value="Substance amount", required = true) ConsumedSubstanceDto request){
        ConsumedSubstance response = service.add(mapper.toEntity(request));
        return new ResponseEntity<>(mapper.toDto(response), HttpStatus.OK);
    }
}
