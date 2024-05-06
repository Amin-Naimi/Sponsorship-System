package com.mohamed.parrinage.service;

import com.mohamed.parrinage.model.MyUser;
import com.mohamed.parrinage.model.dto.Child;
import com.mohamed.parrinage.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

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

    private void updateUserParents(MyUser user) {
        MyUser parent = getUserByAffiliationCode(user.getCodeAffiliationInviter());
        if (parent != null) {
            List<MyUser> parentsOfParent = parent.getParents();
            List<MyUser> updatedParents = new ArrayList<>(parentsOfParent);
            updatedParents.add(parent);
            user.setParents(updatedParents);
        }
    }


    public List<Child> getChildren(Long parentId) {
        return userRepo.findAll()
                .stream()
                .filter(
                        user -> user.getParents() != null
                                &&
                                user.getParents()
                                        .stream()
                                        .anyMatch(parent -> parent.getId().equals(parentId)))
                .map(Child::formEntiyToDto)
                .collect(Collectors.toList());
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
