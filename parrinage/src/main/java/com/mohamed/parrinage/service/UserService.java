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
   // private static final int MAX_LEVEL_1 = 6;
    // private static final int MAX_LEVEL_2 = 3;



    public MyUser registration(MyUser user) {
        if (user != null) {
            user.setCodeAffiliation(generateAffiliationCode(user));
            updateUserParents(user);
            MyUser parrain = getUserByAffiliationCode(user.getCodeAffiliationInviter());
                user.setParrainId(parrain.getId());
            return userRepo.save(user);
        }
        return null;
    }

    private void updateUserParents(MyUser user) {
        MyUser parent = getUserByAffiliationCode(user.getCodeAffiliationInviter());
        if (parent != null) {
            List<MyUser> parentsOfParent = parent.getParents();
            List<MyUser> updatedParents = new ArrayList<>(parentsOfParent);
            updatedParents.add(parent);
            user.setParents(updatedParents);
        }
    }

   /* public List<MyUser> getAllChildrenOfParent(Long ParentID){

    }*/

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
