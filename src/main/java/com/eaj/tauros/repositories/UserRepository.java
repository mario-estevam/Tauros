package com.eaj.tauros.repositories;


import com.eaj.tauros.models.Setor;
import com.eaj.tauros.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserName(String userName);
    List<User> findAllByActiveTrueAndDeleteIsNull();
    List<User> findAllByActiveTrueAndDeleteIsNullAndSetor(Setor setor);
    List<User> findAllByDeleteIsNotNull();
    List<User> findAllByDeleteIsNotNullAndSetor(Setor setor);
    List<User> findAllByActiveFalseAndDeleteIsNull();
    Integer countAllByActiveFalseAndDeleteIsNull();
}
