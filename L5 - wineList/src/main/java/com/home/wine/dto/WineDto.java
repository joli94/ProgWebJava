package com.home.wine.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WineDto {
    @NotNull
    @Size(min=1, max=2)
    private String wineBottles;

    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min=4, max=4)
    private String wineYear;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$")
    @Size(min=3, max=10)
    private String wineColor;

    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min=1, max=2)
    private String wineId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @Size(min=3, max=50)
    private String wineName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$")
    @Size(min=3, max=10)
    private String wineTaste;
}
