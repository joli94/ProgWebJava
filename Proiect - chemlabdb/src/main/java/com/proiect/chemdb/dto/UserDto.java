package com.proiect.chemdb.dto;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @Size(min=5, max=50)
    @Pattern(regexp = "[a-zA-Z ]+$")
    private String name;

    @Size(min=5, max=50)
    @Pattern(regexp = "[a-zA-Z ]+$")
    private String role;

    private Boolean admin;
    private Boolean restrictedAccess;
}