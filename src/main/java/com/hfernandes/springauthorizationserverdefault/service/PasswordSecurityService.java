package com.hfernandes.springauthorizationserverdefault.service;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordSecurityService {

     private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

     public String encode(String plaintextPassword) {
          return passwordEncoder.encode(plaintextPassword);
     }

     public Boolean matches(String plaintextPassword, String encodedPassword) {
          return passwordEncoder.matches(plaintextPassword, encodedPassword);
     }
}