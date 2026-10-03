package com.lenv.lenv_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameCommercial;

    @Column(nullable = false)
    private String principleActive;

    private String concentration;
    private String pharmaceuticalForm;
    private String laboratory;

    @Column(unique = true)
    private String barcode; // GTIN

    private Boolean hasGeneric;
    private Double genericPrice;
    private Double referencePrice;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    private LocalDateTime createdAt = LocalDateTime.now();
}
