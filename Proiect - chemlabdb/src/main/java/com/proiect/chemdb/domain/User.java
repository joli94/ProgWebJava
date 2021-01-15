package com.proiect.chemdb.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @NotNull
    private Long id;

    @Size(min=5, max=50)
    @Pattern(regexp = "[a-zA-Z ]+$")
    private String name;

    @Size(min=5, max=20)
    @Pattern(regexp = "[a-zA-Z ]+$")
    private String role;
    
    private Boolean admin;
    private Boolean restrictedAccess;
}
