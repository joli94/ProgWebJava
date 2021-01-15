package com.proiect.chemdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumedSubstance {
    private Long id;

    @NotNull
    private Long substanceId;

    @NotNull
    private Float consumedAmount;

    @NotNull
    private Long userId;
}