package com.lenv.lenv_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderCode; // ex: LENV-0001

    @Column(nullable = false)
    private String medicationName;

    @Column(nullable = false)
    private String pharmacyName;

    @Column(nullable = false)
    private Double price;

    private Double distanceMeters;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionSignature;
    private String walletPublicKey;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
