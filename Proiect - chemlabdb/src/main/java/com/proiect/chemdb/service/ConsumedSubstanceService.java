package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.domain.ConsumedSubstance;
import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.exception.BadRequestException;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.ConsumedSubstanceRepository;
import com.proiect.chemdb.repository.SubstanceRepository;
import com.proiect.chemdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumedSubstanceService {
    private ConsumedSubstanceRepository consumedRepository;
    private SubstanceRepository substanceRepository;
    private UserRepository userRepository;

    @Autowired
    public ConsumedSubstanceService(ConsumedSubstanceRepository repository, SubstanceRepository substanceRepository, UserRepository userRepository) {
        this.consumedRepository = repository;
        this.substanceRepository = substanceRepository;
        this.userRepository = userRepository;
    }

    public List<ConsumedSubstance> getAll() {
        return consumedRepository.findAll().orElseThrow(() -> new EntityNotFoundException("There is no substance consumed!"));
    }

    public List<ConsumedSubstance> getBySubstance(Long id) {
        return consumedRepository.findBySubstance(id).orElseThrow(() -> new EntityNotFoundException("This substance was not consumed!"));
    }

    public List<ConsumedSubstance> getByUser(Long id) {
        return consumedRepository.findByUser(id).orElseThrow (() -> new EntityNotFoundException("This user hasn't consumed any substance!"));
    }

    public ConsumedSubstance add(ConsumedSubstance substance) {
        Optional<Substance> recordSubstanceInDatabase = substanceRepository.findById(substance.getSubstanceId());

        if (recordSubstanceInDatabase.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no substance with id=%s in the database!", substance.getSubstanceId().toString()));
        }

        Optional<User> recordUserInDatabase = userRepository.findBy(substance.getUserId());

        if(recordUserInDatabase.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no user with id=%s in the database!", substance.getUserId().toString()));
        }

        if (recordSubstanceInDatabase.get().getRestricted()  && !recordUserInDatabase.get().getRestrictedAccess()) {
            throw new BadRequestException(String.format("The user with the id %s isn't allowed to work with the substance having the id %s!", substance.getUserId().toString(), substance.getSubstanceId().toString()));
        }


        if(recordSubstanceInDatabase.get().getAvailableQuantity() < substance.getConsumedAmount()) {
            throw new BadRequestException("The amount entered is higher than the actual amount in the bottle!");
        }

        substanceRepository.updateAvailableQuantity(recordSubstanceInDatabase.get().getAvailableQuantity() - substance.getConsumedAmount(), substance.getSubstanceId());

        return consumedRepository.addEntry(substance);
    }


}
