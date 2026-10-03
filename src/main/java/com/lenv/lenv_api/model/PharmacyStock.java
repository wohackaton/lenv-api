package com.lenv.lenv_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pharmacy_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    private Double price;
    private Integer quantity;
    private Boolean canReserve;
    private LocalDateTime lastUpdated = LocalDateTime.now();
}
