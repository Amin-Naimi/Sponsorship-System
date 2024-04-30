package com.mohamed.parrinage.service;

import com.mohamed.parrinage.model.MyUser;
import com.mohamed.parrinage.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public MyUser registration(MyUser user){
            if (user != null){
                user.setCodeAffiliation(generateAffiliationCode(user));
                updateUserParents(user);
                return userRepo.save(user);
            }
            return null;
    }


    private void updateUserParents(MyUser user) {
        MyUser parent = getUserByAffiliationCode(user.getCodeAffiliationInviter());
        if (parent != null) {
            // Récupérer les parents du parent
            List<MyUser> parentsOfParent = parent.getParents();
            // Créer une nouvelle liste de parents pour l'utilisateur
            List<MyUser> updatedParents = new ArrayList<>(parentsOfParent);
            // Ajouter le parent direct à la liste des parents
            updatedParents.add(parent);
            // Mettre à jour les parents de l'utilisateur
            user.setParents(updatedParents);
        }
    }


    public MyUser getUserByAffiliationCode(String codeAffiliationInviter){
        MyUser user =  userRepo.findUsersByCodeAffiliation(codeAffiliationInviter);
        System.err.println("***** GET USER BY AFFILIATION CODE ****");
        System.err.println(user);
        System.err.println("***************************************");

        return user;
    }
    private String generateAffiliationCode(MyUser user){
        return "CODE_AFF"+user.getLastName();
    }


}
