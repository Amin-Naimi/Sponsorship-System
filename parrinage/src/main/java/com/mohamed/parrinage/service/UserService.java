package com.mohamed.parrinage.service;

import com.mohamed.parrinage.model.MyUser;
import com.mohamed.parrinage.model.Parrainage;
import com.mohamed.parrinage.model.dto.Child;
import com.mohamed.parrinage.model.dto.Filleul;
import com.mohamed.parrinage.repo.ParrinageRepo;
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
    private final ParrinageRepo parrinageRepo;
    private static final int MAX_PARRINAGE_LEVEL = 3;

    public MyUser registration(MyUser user) {
        if (user != null) {
            user.setCodeAffiliation(generateAffiliationCode(user));
            updateUserParents(user);
            MyUser parrain = getUserByAffiliationCode(user.getCodeAffiliationInviter());
            if (parrain != null) {
                user.setParrainId(parrain.getId());
                user.setUserLevelInTheNetwork(parrain.getUserLevelInTheNetwork() + 1);
            } else {
                user.setUserLevelInTheNetwork(0);
            }
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

            int parrinageLevel = 1; // Start with the highest level
            for (int i = updatedParents.size() - 1; i >= 0 && parrinageLevel <= MAX_PARRINAGE_LEVEL; i--) {
                MyUser grandParent = updatedParents.get(i);
                createParrinageEntry(user, grandParent, parrinageLevel);
                parrinageLevel++;
            }
        }
    }

    private void createParrinageEntry(MyUser user, MyUser parent, int parrinageLevel) {
        Parrainage parrainage = new Parrainage();
        parrainage.setUserEmail(user.getEmail());
        parrainage.setParentEmail(parent.getEmail());
        parrainage.setUserId(user.getId());
        parrainage.setParentId(parent.getId());
        parrainage.setParrinageLevel(parrinageLevel);
        parrinageRepo.save(parrainage);
    }

    public List<Filleul> getUserFilleuls(Long userId, String userEmail) {
        return parrinageRepo.findAllByParentIdAndParentEmail(userId, userEmail).stream().map(Filleul::fromEntityToDto).collect(Collectors.toList());
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
