package com.mohamed.parrinage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String codeAffiliation;
    private String codeAffiliationInviter;
    private Long parrainId;
    private Integer userLevelInTheNetwork;
    private Double solde = 50.0;

    @ManyToMany
    @JoinTable(
            name = "user_parents",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private List<MyUser> parents;

}
