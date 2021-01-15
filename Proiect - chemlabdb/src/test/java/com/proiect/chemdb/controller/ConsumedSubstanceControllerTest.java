package com.proiect.chemdb.controller;

import com.proiect.chemdb.domain.ConsumedSubstance;
import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.dto.ConsumedSubstanceDto;
import com.proiect.chemdb.dto.SubstanceDto;
import com.proiect.chemdb.mapper.ConsumedSubstanceMapper;
import com.proiect.chemdb.service.ConsumedSubstanceService;
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
class ConsumedSubstanceControllerTest {
    @Mock
    private ConsumedSubstanceService service;

    @Spy
    private ConsumedSubstanceMapper mapper;

    @InjectMocks
    private ConsumedSubstanceController controller;

    private ConsumedSubstance expected;
    private List<ConsumedSubstance> listExpected = new ArrayList<>();

    @BeforeEach
    void setup() {
        expected = ConsumedSubstance.builder()
                .id(1L)
                .substanceId(1L)
                .consumedAmount(0.0003F)
                .userId(1L)
                .build();

        listExpected.add(expected);
    }

    @Test
    @DisplayName("Add consumed substance - happy flow")
    void addConsumedSubstance() {
        ConsumedSubstanceDto request = ConsumedSubstanceDto.builder()
                .substanceId(1L)
                .consumedAmount(0.0003F)
                .userId(1L)
                .build();

        when(service.add(mapper.toEntity(request))).thenReturn(expected);

        ResponseEntity<ConsumedSubstanceDto> result = controller.add(request);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(expected));
    }

    @Test
    @DisplayName("Get consumed substance by substaance id - happy flow")
    void getBySubstanceId() {
        Long id = 1L;

        when(service.getBySubstance(id)).thenReturn(listExpected);

        ResponseEntity<List<ConsumedSubstanceDto>> result = controller.getBySubstance(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }

    @Test
    @DisplayName("Get consumed substances from by user id - happy flow")
    void test_getUserId() {
        Long id = 1L;

        when(service.getByUser(id)).thenReturn(listExpected);

        ResponseEntity<List<ConsumedSubstanceDto>> result = controller.getByUser(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }

    @Test
    @DisplayName("Get all substances- happy flow")
    void test_getAll() {
        when(service.getAll()).thenReturn(listExpected);

        ResponseEntity<List<ConsumedSubstanceDto>> result = controller.getAll();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mapper.toDto(listExpected));
    }
}