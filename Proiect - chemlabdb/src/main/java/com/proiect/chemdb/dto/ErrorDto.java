package com.proiect.chemdb.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private Integer code;
    private String message;
}
