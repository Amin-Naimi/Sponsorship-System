package com.mohamed.parrinage.controller;

import com.mohamed.parrinage.model.MyUser;
import com.mohamed.parrinage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
