package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User expected;

    @BeforeEach
    void setup(){
        expected = User.builder()
                .id(1L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();
    }

    @Test
    @DisplayName("Create user - happy flow")
    void test_createUser() {
        User request = User.builder()
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();

        when(repository.save(request)).thenReturn(expected);

        User result = service.create(request);

        assertThat(result).isEqualTo(expected);

        verify(repository).save(request);
    }

    @Test
    @DisplayName("Find user by id - happy flow")
    void test_getUserById() {
        Long id = 1L;
        expected.setId(id);

        when(repository.findBy(id)).thenReturn(Optional.of(expected));

        User result = service.getBy(id);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Find user by id - id doesn't exist in the database")
    void test_getUserById_throwsEntityNotFoundException_whenUserNotFound() {
        Long id = 1L;

        when(repository.findBy(id)).thenThrow(new EntityNotFoundException(String.format("There is no user with id=%s in the database!", id.toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getBy(id)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("There is no user with id=%s in the database!", id.toString()));
    }

    @Test
    @DisplayName("Update user - happy flow")
    void test_updateUser() {
        Long id = 1L;
        User request = User.builder()
                .id(id)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();

        when(repository.update(request)).thenReturn(expected);
        when(repository.findBy(id)).thenReturn(Optional.of(expected));

        User result = service.update(request);

        assertThat(result).isEqualTo(expected);
        verify(repository).update(request);
        verify(repository).findBy(id);
    }

    @Test
    @DisplayName("Update user - id doesn't exist in the database")
    void test_updateUserById_throwsEntityNotFoundException_whenUserNotFound() {
        Long id = 1L;

        when(repository.findBy(id)).thenThrow(new EntityNotFoundException(String.format("There is no user with id=%s in the database!", id.toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getBy(id)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("There is no user with id=%s in the database!", id.toString()));

        verify(repository, times(0)).update(any());
        verify(repository).findBy(id);
    }
}