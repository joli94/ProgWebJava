package com.proiect.chemdb.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubstanceDto {

    private Long id;

    @NotBlank
    @Size(min=3, max=20)
    @Pattern(regexp = "[0-9-]+$")
    private String cas;

    @NotBlank
    @Size(min=3, max=100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[A-Za-z0-9]+$")
    private String molecularFormula;

    @NotNull
    private Float purity;

    @NotBlank
    private String supplier;

    @NotBlank
    private String packing;

    @NotNull
    private Float availableQuantity;

    private Integer ownerId; // cheie straina pe user

    @NotNull
    private Boolean restricted;

    @NotNull
    private Float price;

    @NotBlank
    private String link;
}
