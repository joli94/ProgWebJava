package com.home.dogs.domain;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    @NotNull
    private Long id;
    private String name;
    private Integer age;
    private String city;
}
