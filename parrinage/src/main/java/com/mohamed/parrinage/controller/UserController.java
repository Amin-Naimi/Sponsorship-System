package com.mohamed.parrinage.controller;

import com.mohamed.parrinage.model.MyUser;
import com.mohamed.parrinage.model.dto.Child;
import com.mohamed.parrinage.model.dto.Filleul;
import com.mohamed.parrinage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("save/user")
    MyUser saveUser(@RequestBody MyUser user){
        return userService.registration(user);
    }

    @GetMapping("get/user/affiliationCodeInviter")
    MyUser getUserByAffiliationCode(@RequestParam("affCdIn") String affiliationCode){
        return userService.getUserByAffiliationCode(affiliationCode);
    }

    @GetMapping("get/filleul")
    public List<Filleul> getUserfilleul(
                @RequestParam(name = "userID") Long userId,
            @RequestParam("userEmail") String userEmail){
        return userService.getUserFilleuls(userId,userEmail);
    }


}

 /* @GetMapping("get/children")
    public List<Child> getUserChildren(@RequestParam(name = "userID") Long userId){
        return userService.getChildren(userId);
    }*/