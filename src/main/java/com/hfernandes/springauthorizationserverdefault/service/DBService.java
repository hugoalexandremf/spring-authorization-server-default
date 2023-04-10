package com.hfernandes.springauthorizationserverdefault.service;

import com.hfernandes.springauthorizationserverdefault.db.model.User;
import com.hfernandes.springauthorizationserverdefault.db.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DBService {

     private static final Logger LOG = LoggerFactory.getLogger(DBService.class);
     
     @Autowired
     private UserRepository userRepository;

     //User
     public User getUserByUsername(String username) {
          Optional<User> optionalUser = null;
          try {
               optionalUser = userRepository.findUserByUsername(username);
          } catch (DataAccessException e) {
               LOG.error("E, sT:", e);
          }

          return optionalUser.get();
     }

     public User insertUser(User userToInsert) {
          return userRepository.save(userToInsert);
     }
}
