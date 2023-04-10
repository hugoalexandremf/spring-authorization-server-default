package com.hfernandes.springauthorizationserverdefault.db.repository;

import com.hfernandes.springauthorizationserverdefault.db.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

     Optional<Client> findByClientId(String clientID);

}