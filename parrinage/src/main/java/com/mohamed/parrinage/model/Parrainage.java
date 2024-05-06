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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MyUser parent;
    private String parentEmail;
    private int parrinageLevel;


}
//private Double commissionAmount;
//private LocalDateTime transactionDate;