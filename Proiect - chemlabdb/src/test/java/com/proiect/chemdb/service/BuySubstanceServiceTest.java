package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.BuySubstanceRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuySubstanceServiceTest {

    @Mock
    private BuySubstanceRepository repository;

    @InjectMocks
    private BuySubstanceService service;

    private BuySubstance expected;
    private List<BuySubstance> listExpected = new ArrayList<>();


    @BeforeEach
    void setup() {
        expected = BuySubstance.builder()
                .id(1L)
                .cas("3443456")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .number(11L)
                .userId(1L)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();

        listExpected.add(expected);
    }

    @Test
    @DisplayName("Create substance - happy flow")
    void test_createSubstance() {
        BuySubstance request = BuySubstance.builder()
                .cas("3443456")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .number(11L)
                .userId(1L)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();

        when(repository.save(request)).thenReturn(expected);

        BuySubstance result = service.create(request);

        assertThat(result).isEqualTo(expected);
        verify(repository).save(request);
    }

    @Test
    @DisplayName("Find all substances - happy flow")
    void test_getAllBuySubstances() {
        when(repository.getAll()).thenReturn(Optional.of(listExpected));

        List<BuySubstance> result = service.getAll();

        assertThat(result).isEqualTo(listExpected);

        verify(repository).getAll();
    }

    @Test
    @DisplayName("Find all substance - no substance is present in the database")
    void test_getAllBuySubstances_throwsEntityNotFoundException_whenSubstanceNotFound() {
        when(repository.getAll()).thenThrow(new EntityNotFoundException("There is no substance to be purchased"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getAll()
        );

        assertThat(exception.getMessage()).isEqualTo("There is no substance to be purchased");
        verify(repository).getAll();
    }

    @Test
    @DisplayName("Find substance by user  - happy flow")
    void test_getSubstanceByUser() {
        Long id  = 1L;

        when(repository.getById(id)).thenReturn(Optional.of(listExpected));

        List<BuySubstance> result = service.getByUser(id);

        assertThat(result).isEqualTo(listExpected);
        verify(repository).getById(id);
    }

    @Test
    @DisplayName("Find substance by user - no substance from that user exists in the database")
    void test_getSubstanceByUser_throwsEntityNotFoundException_whenSubstanceNotFound() {
        Long id = 1L;

        when(repository.getById(id)).thenThrow(new EntityNotFoundException(String.format("The used with the id %s didn't add any substance to be purchased", id.toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getByUser(id)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("The used with the id %s didn't add any substance to be purchased", id.toString()));
        verify(repository).getById(id);
    }
}