package com.hfernandes.springauthorizationserverdefault.db.repository;

import com.hfernandes.springauthorizationserverdefault.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findUserByUsername(String username);

}
