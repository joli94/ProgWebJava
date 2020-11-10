package com.home.wine.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wine {
    private String bottles;
    private String year;

    private String color;
    private String id;
    private String name;
    private String taste;
}
