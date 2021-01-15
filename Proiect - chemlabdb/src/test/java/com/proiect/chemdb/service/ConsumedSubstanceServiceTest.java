package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.ConsumedSubstance;
import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.exception.BadRequestException;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.ConsumedSubstanceRepository;
import com.proiect.chemdb.repository.SubstanceRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumedSubstanceServiceTest {
    @Mock
    private ConsumedSubstanceRepository consumedSubstanceRepository;

    @Mock
    private SubstanceRepository  substanceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConsumedSubstanceService service;


    private ConsumedSubstance expected;
    private List<ConsumedSubstance> listExpected = new ArrayList<>();

    @BeforeEach
    void setup() {
        expected = ConsumedSubstance.builder()
                .id(1L)
                .substanceId(1L)
                .consumedAmount(0.0001F)
                .userId(1L)
                .build();

        listExpected.add(expected);
    }

    @Test
    @DisplayName("Add consumed substance - happy flow")
    void test_addConsumedSubstance() {
        ConsumedSubstance request = ConsumedSubstance.builder()
                .substanceId(1L)
                .consumedAmount(0.0001F)
                .userId(1L)
                .build();
        Substance sRequest = Substance.builder()
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20H16O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(false)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();
        User uRequest = User.builder()
                .id(1L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();

        when(substanceRepository.findById(request.getSubstanceId())).thenReturn(Optional.of(sRequest));
        when(userRepository.findBy(request.getUserId())).thenReturn(Optional.of(uRequest));
        when(consumedSubstanceRepository.addEntry(request)).thenReturn(expected);

        ConsumedSubstance result = service.add(request);

        assertThat(result).isEqualTo(expected);
        verify(substanceRepository).findById(request.getSubstanceId());
        verify(userRepository).findBy(request.getUserId());
        verify(substanceRepository).updateAvailableQuantity(sRequest.getAvailableQuantity()-request.getConsumedAmount(), request.getSubstanceId());
    }

    @Test
    @DisplayName("Add consumed substance - wrong substance id")
    void test_addConsumedSubstance_throwsEntityNotFoundException_whenSubstanceNotFound() {
        ConsumedSubstance request = ConsumedSubstance.builder()
                .substanceId(1L)
                .consumedAmount(0.0001F)
                .userId(1L)
                .build();
        Substance sRequest = Substance.builder()
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20H16O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(false)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();

        when(substanceRepository.findById(request.getSubstanceId())).thenThrow(new EntityNotFoundException(String.format("There is no substance with id=%s in the database!", request.getSubstanceId().toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.add(request)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("There is no substance with id=%s in the database!", request.getSubstanceId().toString()));

        verify(substanceRepository).findById(request.getSubstanceId());
        verify(userRepository, times(0)).findBy(request.getUserId());
        verify(substanceRepository, times(0)).updateAvailableQuantity(sRequest.getAvailableQuantity()-request.getConsumedAmount(), request.getSubstanceId());
    }

    @Test
    @DisplayName("Add consumed substance - wrong user id")
    void test_addConsumedSubstance_throwsEntityNotFoundException_whenUserNotFound() {
        ConsumedSubstance request = ConsumedSubstance.builder()
                .substanceId(1L)
                .consumedAmount(0.0001F)
                .userId(1L)
                .build();
        Substance sRequest = Substance.builder()
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20H16O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(false)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();
        User uRequest = User.builder()
                .id(1L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();

        when(substanceRepository.findById(request.getSubstanceId())).thenReturn(Optional.of(sRequest));
        when(userRepository.findBy(request.getUserId())).thenThrow(new EntityNotFoundException(String.format("There is no user with id=%s in the database!", request.getUserId().toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.add(request)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("There is no user with id=%s in the database!", request.getUserId().toString()));

        verify(substanceRepository).findById(request.getSubstanceId());
        verify(userRepository).findBy(request.getUserId());
        verify(substanceRepository, times(0)).updateAvailableQuantity(sRequest.getAvailableQuantity()-request.getConsumedAmount(), request.getSubstanceId());
    }

    @Test
    @DisplayName("Add consumed substance - the user is not allowed to work with the substance")
    void test_addConsumedSubstance_throwsBadRequestException_whenUserNotAllowed() {
        ConsumedSubstance request = ConsumedSubstance.builder()
                .substanceId(1L)
                .consumedAmount(0.0001F)
                .userId(1L)
                .build();
        Substance sRequest = Substance.builder()
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20H16O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(true)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();
        User uRequest = User.builder()
                .id(1L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(false)
                .build();

        when(substanceRepository.findById(request.getSubstanceId())).thenReturn(Optional.of(sRequest));
        when(userRepository.findBy(request.getUserId())).thenReturn(Optional.of(uRequest));

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                service.add(request)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("The user with the id %s isn't allowed to work with the substance having the id %s!", request.getUserId().toString(), request.getSubstanceId().toString()));

        verify(substanceRepository).findById(request.getSubstanceId());
        verify(userRepository).findBy(request.getUserId());
        verify(substanceRepository, times(0)).updateAvailableQuantity(sRequest.getAvailableQuantity()-request.getConsumedAmount(), request.getSubstanceId());
    }

    @Test
    @DisplayName("Add consumed substance - the consumed amount is higher than the actual amount")
    void test_addConsumedSubstance_throwsBadRequestException_whenNoeEnoughAmount() {
        ConsumedSubstance request = ConsumedSubstance.builder()
                .substanceId(1L)
                .consumedAmount(1.0001F)
                .userId(1L)
                .build();
        Substance sRequest = Substance.builder()
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20H16O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(false)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();
        User uRequest = User.builder()
                .id(1L)
                .name(RandomStringUtils.randomAlphabetic(30))
                .role(RandomStringUtils.randomAlphabetic(20))
                .admin(false)
                .restrictedAccess(true)
                .build();

        when(substanceRepository.findById(request.getSubstanceId())).thenReturn(Optional.of(sRequest));
        when(userRepository.findBy(request.getUserId())).thenReturn(Optional.of(uRequest));

        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () ->
                service.add(request)
        );

        assertThat(exception.getMessage()).isEqualTo("The amount entered is higher than the actual amount in the bottle!");

        verify(substanceRepository).findById(request.getSubstanceId());
        verify(userRepository).findBy(request.getUserId());
        verify(substanceRepository, times(0)).updateAvailableQuantity(sRequest.getAvailableQuantity()-request.getConsumedAmount(), request.getSubstanceId());
    }

    @Test
    @DisplayName("Find all the consumed substances - happy flow")
    void test_findAllConsumedSubstances() {
        when(consumedSubstanceRepository.findAll()).thenReturn(Optional.of(listExpected));

        List<ConsumedSubstance> result = service.getAll();

        assertThat(result).isEqualTo(listExpected);
        verify(consumedSubstanceRepository).findAll();
    }

    @Test
    @DisplayName("Find all the consumed substance - no substance is present in the database")
    void test_findAllConsumedSubstances_throwsEntityNotFoundException_whenSubstanceNotFound() {
        when(consumedSubstanceRepository.findAll()).thenThrow(new EntityNotFoundException("There is no substance consumed!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getAll()
        );

        assertThat(exception.getMessage()).isEqualTo("There is no substance consumed!");
        verify(consumedSubstanceRepository).findAll();
    }

    @Test
    @DisplayName("Find the consumed substances by substance id - happy flow")
    void test_findConsumedSubstancesBySubstanceId() {
        Long id = 1L;

        when(consumedSubstanceRepository.findBySubstance(id)).thenReturn(Optional.of(listExpected));

        List<ConsumedSubstance> result = service.getBySubstance(id);

        assertThat(result).isEqualTo(listExpected);
        verify(consumedSubstanceRepository).findBySubstance(id);
    }

    @Test
    @DisplayName("Find the consumed substance by substance id - no substance is present in the database")
    void test_findConsumedSubstancesBySubstanceId_throwsEntityNotFoundException_whenSubstanceNotFound() {
        Long id = 1L;

        when(consumedSubstanceRepository.findBySubstance(id)).thenThrow(new EntityNotFoundException("This substance was not consumed!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getBySubstance(id)
        );

        assertThat(exception.getMessage()).isEqualTo("This substance was not consumed!");
        verify(consumedSubstanceRepository).findBySubstance(id);
    }

    @Test
    @DisplayName("Find the consumed substances by user id - happy flow")
    void test_findConsumedSubstancesByUserId() {
        Long id = 1L;

        when(consumedSubstanceRepository.findByUser(id)).thenReturn(Optional.of(listExpected));

        List<ConsumedSubstance> result = service.getByUser(id);

        assertThat(result).isEqualTo(listExpected);
        verify(consumedSubstanceRepository).findByUser(id);
    }

    @Test
    @DisplayName("Find the consumed substance by user id - no substance is present in the database")
    void test_findConsumedSubstancesByUserId_throwsEntityNotFoundException_whenSubstanceNotFound() {
        Long id = 1L;

        when(consumedSubstanceRepository.findByUser(id)).thenThrow(new EntityNotFoundException("This user hasn't consumed any substance!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getByUser(id)
        );

        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("This user hasn't consumed any substance!");
        verify(consumedSubstanceRepository).findByUser(id);
    }
}