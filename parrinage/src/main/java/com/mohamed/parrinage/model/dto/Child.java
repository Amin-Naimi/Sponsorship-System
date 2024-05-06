package com.mohamed.parrinage.model.dto;

import com.mohamed.parrinage.model.MyUser;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Child {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String codeAffiliation;
    private String codeAffiliationInviter;
    private Long parrainId;

    public static Child formEntiyToDto(MyUser user){
        return Child.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .codeAffiliationInviter(user.getCodeAffiliationInviter())
                .codeAffiliation(user.getCodeAffiliation())
                .parrainId(user.getParrainId())
                .build();
    }

}
