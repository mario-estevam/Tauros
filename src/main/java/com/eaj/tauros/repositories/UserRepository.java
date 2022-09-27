package com.eaj.tauros.repositories;


import com.eaj.tauros.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserName(String userName);
    List<User> findAllByActiveTrue();
    List<User> findAllByActiveFalse();
}
