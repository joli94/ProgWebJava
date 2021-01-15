package com.proiect.chemdb.service;

import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.exception.EntityNotFoundException;
import com.proiect.chemdb.repository.SubstanceRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubstanceServiceTest {

    @Mock
    private SubstanceRepository repository;

    @InjectMocks
    private SubstanceService service;

    private Substance expected;

    @BeforeEach
    void setup() {
        expected = Substance.builder()
                .id(1L)
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
    }

    @Test
    @DisplayName("Create substance - happy flow")
    void test_createSubstance() {
        Substance request = Substance.builder()
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

        when(repository.save(request)).thenReturn(expected);

        Substance result = service.create(request);

        assertThat(result).isEqualTo(expected);
        verify(repository).save(request);
    }

    @Test
    @DisplayName("Find substance by id - happy flow")
    void test_getSubstanceById() {
        Long id = 1L;
        expected.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(expected));

        Substance result = service.getById(id);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Find substance by id - id doesn't exist in the database")
    void test_getSubstanceById_throwsEntityNotFoundException_whenSubstanceNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenThrow(new EntityNotFoundException(String.format("There is no substance with id=%s in the database!", id.toString())));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getById(id)
        );

        assertThat(exception.getMessage()).isEqualTo(String.format("There is no substance with id=%s in the database!", id.toString()));
    }

    @Test
    @DisplayName("Find substance by query  - cas - happy flow")
    void test_getSubstanceByCas() {
        String query = "3443456";
        List<Substance> listExpected = new ArrayList<>();
        listExpected.add(expected);

        when(repository.findByCas(query)).thenReturn(Optional.of(listExpected));

        List<Substance> result = service.getByQuery(query);

        assertThat(result).isEqualTo(listExpected);
        verify(repository).findByCas(query);
        verify(repository,times(0)).findByMolecularFormula(any());
        verify(repository,times(0)).findByName(any());
    }

    @Test
    @DisplayName("Find substance by query - cas - cas doesn't exist in the database")
    void test_getSubstanceByCas_throwsEntityNotFoundException_whenSubstanceNotFound() {
        String query = "3443457";

        when(repository.findByCas(query)).thenThrow(new EntityNotFoundException("The substance with this CAS was not found in the database!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getByQuery(query)
        );

        assertThat(exception.getMessage()).isEqualTo("The substance with this CAS was not found in the database!");
        verify(repository).findByCas(query);
        verify(repository,times(0)).findByMolecularFormula(any());
        verify(repository,times(0)).findByName(any());
    }

    @Test
    @DisplayName("Find substance by query  - molecular formula - happy flow")
    void test_getSubstanceByMolecularFormula() {
        String query = "C20H16O2";
        List<Substance> listExpected = new ArrayList<>();
        listExpected.add(expected);

        when(repository.findByMolecularFormula(query)).thenReturn(Optional.of(listExpected));

        List<Substance> result = service.getByQuery(query);

        assertThat(result).isEqualTo(listExpected);
        verify(repository,times(0)).findByCas(any());
        verify(repository).findByMolecularFormula(query);
        verify(repository,times(0)).findByName(any());
    }

    @Test
    @DisplayName("Find substance by query - molecular formula - molecular formula doesn't exist in the database")
    void test_getSubstanceByMolecularFormula_throwsEntityNotFoundException_whenSubstanceNotFound() {
        String query = "C21H15O2";

        when(repository.findByMolecularFormula(query)).thenThrow(new EntityNotFoundException("No substance in the database have this molecular formula!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getByQuery(query)
        );

        assertThat(exception.getMessage()).isEqualTo("No substance in the database have this molecular formula!");
        verify(repository,times(0)).findByCas(any());
        verify(repository).findByMolecularFormula(query);
        verify(repository,times(0)).findByName(any());
    }

    @Test
    @DisplayName("Find substance by query  - name - happy flow")
    void test_getSubstanceByName() {
        String query = "1-pyrenebutiric acid";
        List<Substance> listExpected = new ArrayList<>();
        listExpected.add(expected);

        when(repository.findByName(query)).thenReturn(Optional.of(listExpected));

        List<Substance> result = service.getByQuery(query);

        assertThat(result).isEqualTo(listExpected);
        verify(repository,times(0)).findByCas(any());
        verify(repository, times(0)).findByMolecularFormula(any());
        verify(repository).findByName(query);
    }

    @Test
    @DisplayName("Find substance by query - name - name doesn't exist in the database")
    void test_getSubstanceByName_throwsEntityNotFoundException_whenSubstanceNotFound() {
        String query = "1-nepyrenebutiric acid";

        when(repository.findByName(query)).thenThrow(new EntityNotFoundException("The substance with this name was not found in the database!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getByQuery(query)
        );

        assertThat(exception.getMessage()).isEqualTo("The substance with this name was not found in the database!");
        verify(repository,times(0)).findByCas(any());
        verify(repository,times(0)).findByMolecularFormula(any());
        verify(repository).findByName(query);
    }

    @Test
    @DisplayName("Find substance by ownerId  - happy flow")
    void test_getSubstancesByOwnerId() {
        Long id = 1L;

        List<Substance> listExpected = new ArrayList<>();
        listExpected.add(expected);

        when(repository.findByUser(id)).thenReturn(Optional.of(listExpected));

        List<Substance> result = service.getByUser(id);

        assertThat(result).isEqualTo(listExpected);
        verify(repository).findByUser(id);
    }

    @Test
    @DisplayName("Find substance by ownerId - this user hasn't any propietary substances")
    void test_getSubstanceBOwnerId_throwsEntityNotFoundException_whenNoSubstanceIsFound() {
        Long id = 1L;

        when(repository.findByUser(id)).thenThrow(new EntityNotFoundException("This user has no proprietary substances!"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.getByUser(id)
        );

        assertThat(exception.getMessage()).isEqualTo("This user has no proprietary substances!");
        verify(repository).findByUser(id);
    }
}