package com.mohamed.parrinage.model.dto;

import com.mohamed.parrinage.model.Parrainage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filleul {
    private long id;
    private String filleulEmail;
    private int filleulLevel;
    private LocalDateTime lastCommissionDate;
    private double totalCommissionEarned;
    private double lastCommissionAmount;
    public static Filleul fromEntityToDto (Parrainage parrainage){
        return Filleul.builder()
                .id(parrainage.getId())
                .filleulEmail(parrainage.getUserEmail())
                .filleulLevel(parrainage.getParrinageLevel())
                .lastCommissionDate(parrainage.getLastCommissionDate())
                .lastCommissionAmount(parrainage.getLastCommissionAmount())
                .totalCommissionEarned(parrainage.getTotalCommissionEarned())
                .build();
    }

    public static void updateParrinageData(Parrainage parrainage,Filleul filleul){
        parrainage.setLastCommissionDate(filleul.getLastCommissionDate());
        parrainage.setLastCommissionAmount(filleul.getLastCommissionAmount());
        parrainage.setTotalCommissionEarned(filleul.getTotalCommissionEarned());
    }
}
