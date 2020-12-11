package com.home.dogs.dto;


import com.home.dogs.validators.RequiredIf;
import lombok.*;

import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@RequiredIf(message = "Owner field invalid!")
public class DogDto {

    private Long id;

    @Size(min=3, max=50)
    private String name;
    private Integer age;
    private String breed;
    private String color;

    private Boolean hasCode;
    private String code;

    private Boolean hasOwner;
    //private PersonDto owner;
}
