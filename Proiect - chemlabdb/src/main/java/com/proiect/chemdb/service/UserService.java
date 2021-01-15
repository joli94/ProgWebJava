package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getBy(Long id) {
        return repository.findBy(id).orElseThrow(() -> new EntityNotFoundException(String.format("There is no user with id=%s in the database!", id.toString())));
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(User user) {

        if (repository.findBy(user.getId()).isPresent()) {
            return repository.update(user);
        } else {
            throw new EntityNotFoundException(String.format("There is no user with id=%s in the database!", user.getId().toString()));
        }
    }
}
