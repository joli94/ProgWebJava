package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.BuySubstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuySubstanceService {
    private final BuySubstanceRepository repository;

    @Autowired
    public BuySubstanceService(BuySubstanceRepository repository) {
        this.repository = repository;
    }

    public List<BuySubstance> getAll() {
        return repository.getAll().orElseThrow(() -> new EntityNotFoundException("There is no substance to be purchased"));
    }

    public List<BuySubstance> getByUser(Long id){
        return repository.getById(id).orElseThrow(() -> new EntityNotFoundException(String.format("The used with the id %s didn't add any substance to be purchased", id.toString())));
    }

    public BuySubstance create(BuySubstance substance) {
        return repository.save(substance);
    }


}
