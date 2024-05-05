package com.mohamed.parrinage.repo;

import com.mohamed.parrinage.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<MyUser,Long> {
    MyUser findUsersByCodeAffiliation(String code);
}
