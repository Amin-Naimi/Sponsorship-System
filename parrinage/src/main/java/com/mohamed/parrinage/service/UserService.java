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
                updateUserParents(user, new ArrayList<>());
                return userRepo.save(user);
            }
            return null;
    }


    private void updateUserParents(MyUser user, List<MyUser> parents) {
        MyUser parent = getUserByAffiliationCode(user.getCodeAffiliationInviter());
        if (parent != null) {
            // Créer une nouvelle liste de parents pour chaque appel récursif//Cela garantit que chaque appel récursif utilise une copie distincte de la liste parents, évitant ainsi les modifications inattendues et les effets secondaires
            List<MyUser> updatedParents = new ArrayList<>(parents);
            updatedParents.add(parent);
            updateUserParents(parent, updatedParents);
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
