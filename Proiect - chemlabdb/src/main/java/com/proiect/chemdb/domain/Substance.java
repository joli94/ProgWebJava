package com.proiect.chemdb.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Substance {

    private Long id;
    private String cas;
    private String name;
    private String molecularFormula;
    private Float purity;
    private String supplier;
    private String packing;
    private Float availableQuantity;
    private Integer ownerId; // cheie straina pe user
    private Boolean restricted;
    private Float price;
    private String link;
}
