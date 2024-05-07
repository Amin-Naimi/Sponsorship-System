package com.mohamed.parrinage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parrainage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private Long parentId;
    private String parentEmail;
    private int parrinageLevel;
    private LocalDateTime joinNetworkDate;
    private LocalDateTime lastCommissionDate;
    private double totalCommissionEarned;
    private double lastCommissionAmount;

    // ajouter un service de historique pour recuperer tous les transaction


}