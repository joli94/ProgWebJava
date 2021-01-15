package com.proiect.chemdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuySubstance {
    @NotNull
    private Long id;
    @NotBlank
    private String cas;

    @NotNull
    private Float purity;
    @NotBlank
    private String supplier;
    @NotBlank
    private String packing;

    @NotNull
    private Long number;
    @NotNull
    private Long userId;

    @NotNull
    private Float price;
    @NotBlank
    private String link;
}
