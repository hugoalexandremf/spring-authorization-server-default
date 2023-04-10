package com.hfernandes.springauthorizationserverdefault.service;

import com.hfernandes.springauthorizationserverdefault.auxobjects.utils.Utils;
import com.hfernandes.springauthorizationserverdefault.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

     private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

     @Autowired
     DBService dbService;


     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          LOG.info("loadUserByUsername:{}", username);

          User user = dbService.getUserByUsername(Utils.normalizeToLowerCase(username));

          return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("secureapi"));
     }
}