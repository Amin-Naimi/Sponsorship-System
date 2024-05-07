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
            //get affiliation cdode
            user.setCodeAffiliation(generateAffiliationCode(user));
            // update parents
            updateUserParents(user);
            //setting level in the global network
            MyUser parrain = getUserByAffiliationCode(user.getCodeAffiliationInviter());
            if(parrain != null){
                user.setParrainId(parrain.getId());
                user.setUserLevelInTheNetwork(parrain.getUserLevelInTheNetwork() + 1);
            }else {
                user.setUserLevelInTheNetwork(0);
            }
            //saving the user
            return userRepo.save(user);
        }
        return null;
    }



    private void updateUserParents(MyUser user) {
        //test if user have a parent
        MyUser parent = getUserByAffiliationCode(user.getCodeAffiliationInviter());
        if (parent != null) {
            List<MyUser> parentsOfParent = parent.getParents();
            List<MyUser> updatedParents = new ArrayList<>(parentsOfParent);
            // add the parent to the parent list
            updatedParents.add(parent);
            user.setParents(updatedParents);

            int parrinageLevel = 1; // Commencez par le niveau le plus élevé
            for (int i = updatedParents.size() - 1; i >= 0; i--) {
                MyUser grandParent = updatedParents.get(i);
                createParrinageEntry(user, grandParent, parrinageLevel);
                parrinageLevel++;
            }
        }
    }

    private void createParrinageEntry(MyUser user, MyUser parent, int parrinageLevel){
        Parrainage parrainage = new Parrainage();
        parrainage.setUserEmail(user.getEmail());
        parrainage.setParentEmail(parent.getEmail());
        parrainage.setParrinageLevel(parrinageLevel);
        parrinageRepo.save(parrainage);

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
