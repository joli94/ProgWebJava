package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.exception.BadRequestException;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.SubstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubstanceService {
    private final SubstanceRepository repository;

    @Autowired
    public SubstanceService(SubstanceRepository repository) {
        this.repository = repository;
    }

    public Substance getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no substance with id=%s om the database!", id.toString())));
    }

    public List<Substance> getByQuery(String query) {
        if(query.matches("[0-9-]+")){
            query = query.replace("-", "");
            return repository.findByCas(query).orElseThrow(() -> new EntityNotFoundException("The substance with this CAS was not found in the database!"));
        }

        if(query.matches("[A-Z0-9 ]+$")){
            return repository.findByMolecularFormula(query).orElseThrow(() -> new EntityNotFoundException(("No substance in the database have this molecular formula!")));
        }

        return repository.findByName(query).orElseThrow(() -> new EntityNotFoundException("The substance with this name was not found in the database!"));
    }

    public List<Substance> getByUser(Long id) {
        return repository.findByUser(id).orElseThrow(() -> new EntityNotFoundException("This user has no proprietary substances!"));
    }

    public Substance create(Substance substance) {
        return repository.save(substance);
    }
}
