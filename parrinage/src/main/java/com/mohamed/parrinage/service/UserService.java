package com.mohamed.parrinage.service;

import com.mohamed.parrinage.model.MyUser;
import com.mohamed.parrinage.model.Parrainage;
import com.mohamed.parrinage.repo.ParrinageRepo;
import com.mohamed.parrinage.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final ParrinageRepo parrinageRepo;

    public MyUser registration(MyUser user) {
        if (user != null) {
            user.setCodeAffiliation(generateAffiliationCode(user));
            updateUserParents(user);

            MyUser parrain = getUserByAffiliationCode(user.getCodeAffiliationInviter());
            if(parrain != null){
                user.setParrainId(parrain.getId());
                user.setUserLevelInTheNetwork(parrain.getUserLevelInTheNetwork() + 1);

            }else {
                user.setUserLevelInTheNetwork(0);
            }
            return userRepo.save(user);
        }
        return null;
    }


    private void createParrinageEntry(MyUser user, MyUser parent, int parrinageLevel){
        Parrainage parrainage = new Parrainage();
        parrainage.setUser(user);
        parrainage.setUserEmail(user.getEmail());
        parrainage.setParent(parent);
        parrainage.setParentEmail(parent.getEmail());
        parrainage.setParrinageLevel(parrinageLevel);
        parrinageRepo.save(parrainage);

    }
    private void updateUserParents(MyUser user) {
        MyUser parent = getUserByAffiliationCode(user.getCodeAffiliationInviter());
        if (parent != null) {
            List<MyUser> parentsOfParent = parent.getParents();
            List<MyUser> updatedParents = new ArrayList<>(parentsOfParent);
            updatedParents.add(parent);
            user.setParents(updatedParents);
            createParrinageEntry(user, parent, user.getUserLevelInTheNetwork());

        }
    }


    public MyUser getUserByAffiliationCode(String codeAffiliationInviter) {
        MyUser user = userRepo.findUsersByCodeAffiliation(codeAffiliationInviter);
        System.err.println("***** GET USER BY AFFILIATION CODE ****");
        System.err.println(user);
        System.err.println("***************************************");

        return user;
    }

    private String generateAffiliationCode(MyUser user) {
        return "CODE_AFF" + user.getLastName();
    }
}
