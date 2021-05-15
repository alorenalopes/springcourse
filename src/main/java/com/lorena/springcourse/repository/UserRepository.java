package com.lorena.springcourse.repository;

import java.util.Optional;


import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.domain.enums.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM user u WHERE email = ?1 AND password = ?2")
    public Optional<User> login(String email, String password);
    //Optional utilizado para tratar valores nulos.

    @Transactional(readOnly = false)
    @Modifying
    @Query("UPDATE user SET role = ?2 WHERE id = ?1")
    public int updateRole(Long id, Role role);
    
}
