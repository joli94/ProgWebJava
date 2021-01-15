package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.dto.BuySubstanceDto;
import com.proiect.chemdb.dto.SubstanceDto;
import com.proiect.chemdb.mapper.BuySubstanceMapper;
import com.proiect.chemdb.service.BuySubstanceService;
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
class BuySubstanceControllerTest {
    @Mock
    private BuySubstanceService service;

    @Spy
    private BuySubstanceMapper mapper;

    @InjectMocks
    private BuySubstanceController controller;

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
                .number(1L)
                .userId(1L)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();
        listExpected.add(expected);
    }

    @Test
    @DisplayName("Create substance - happy flow")
    void test_createSubstance() {
        BuySubstanceDto request = BuySubstanceDto.builder()
                .cas("3443456")
                .purity(97F)
                .supplier("Aldrich")
                .packing("1g")
                .number(1L)
                .userId(1L)
                .price(69.3F)
                .link("https://www.sigmaaldrich.com/catalog/product/aldrich/257354?")
                .build();

        when(service.create(mapper.toEntity(request))).thenReturn(expected);

        ResponseEntity<BuySubstanceDto> result = controller.create(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));

    }

    @Test
    @DisplayName("Get all substances- happy flow")
    void test_getSubstanceById() {
        when(service.getAll()).thenReturn(listExpected);

        ResponseEntity<List<BuySubstanceDto>> result = controller.getAll();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }

    @Test
    @DisplayName("Get substances from user id - happy flow")
    void test_getSearch() {
        Long id = 1L;

        when(service.getByUser(id)).thenReturn(listExpected);

        ResponseEntity<List<BuySubstanceDto>> result = controller.getByUser(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }
}