package com.home.dogs.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDto {
    private Long id;

    @Pattern(regexp = "[a-zA-Z +$]")
    private String name;

    @Min(15)
    private Integer age;
    private String city;

}
