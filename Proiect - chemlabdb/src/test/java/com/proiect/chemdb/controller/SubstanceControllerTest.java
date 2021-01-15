package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.dto.SubstanceDto;
import com.proiect.chemdb.mapper.SubstanceMapper;
import com.proiect.chemdb.service.SubstanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubstanceControllerTest {
    @Mock
    private SubstanceService service;

    @Spy
    private SubstanceMapper mapper;

    @InjectMocks
    private SubstanceController controller;

    private Substance expected;
    private List<Substance> listExpected = new ArrayList<>();

    @BeforeEach
    void setup() {
        expected = Substance.builder()
                .id(1L)
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20 H16 O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(false)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();

        listExpected.add(expected);
    }

    @Test
    @DisplayName("Create substance - happy flow")
    void test_createSubstance() {
        SubstanceDto request = SubstanceDto.builder()
                .cas("3443456")
                .name("1-pyrenebutiric acid")
                .molecularFormula("C20 H16 O2")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .availableQuantity(1F)
                .ownerId(1)
                .restricted(false)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();

        when(service.create(mapper.toEntity(request))).thenReturn(expected);

        ResponseEntity<SubstanceDto> result = controller.create(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));

    }

    @Test
    @DisplayName("Get substance by id - happy flow")
    void test_getSubstanceById() {
        Long id = 1L;

        when(service.getById(id)).thenReturn(expected);

        ResponseEntity<SubstanceDto> result = controller.getById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));
    }

    @Test
    @DisplayName("Get substances from search - happy flow")
    void test_getSearch() {
        String query = "3443-45-6";

        when(service.getByQuery(query)).thenReturn(listExpected);

        ResponseEntity<List<SubstanceDto>> result = controller.getByQuery(query);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }

    @Test
    @DisplayName("Get substances from users by id - happy flow")
    void getOwnerId() {
        Long id = 1L;

        when(service.getByUser(id)).thenReturn(listExpected);

        ResponseEntity<List<SubstanceDto>> result = controller.getByUser(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }
}