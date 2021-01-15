package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.dto.UserDto;
import com.proiect.chemdb.exception.BadRequestException;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.mapper.UserMapper;
import com.proiect.chemdb.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService service;

    @Spy
    private UserMapper mapper;

    @InjectMocks
    private UserController controller;

    private User expected;

    @BeforeEach
    void setup() {
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
        UserDto request = UserDto.builder()
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();

        when(service.create(mapper.toEntity(request))).thenReturn(expected);

        ResponseEntity<UserDto> result = controller.create(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));

    }

    @Test
    @DisplayName("Get user - happy flow")
    void test_getUserById() {
        Long id = 1L;

        when(service.getBy(id)).thenReturn(expected);

        ResponseEntity<UserDto> result = controller.get(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));
    }

    @Test
    @DisplayName("Get user - user with the given id doesn't exist")
    void test_getUserById_throwsEntityNotFoundException_whenUserNotFound() {
        Long id = 1L;

        when(service.getBy(id)).thenThrow(new EntityNotFoundException(String.format("There is no user with id=%s in the database!", id.toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                controller.get(id)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("There is no user with id=%s in the database!", id.toString()));
    }

    @Test
    @DisplayName("Update user - happy flow")
    void test_updateUser() {
        Long id = 1L;

        UserDto request = UserDto.builder()
                .id(1L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .restrictedAccess(true)
                .build();

        when(service.update(mapper.toEntity(request))).thenReturn(expected);

        ResponseEntity<UserDto> result = controller.update(id, request);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));

        verify(service).update(mapper.toEntity(request));
    }

    @Test
    @DisplayName("Update user - different ids between the body and the link")
    void test_updateUser_DifferentIdException() {
        Long id = 1L;

        UserDto request = UserDto.builder()
                .id(2L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .restrictedAccess(true)
                .build();

        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () ->
                controller.update(id, request)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("The path variable doesn't match the request body id!", id.toString()));

        verify(service, times(0)).update(mapper.toEntity(request));
    }
}