package com.hfernandes.springauthorizationserverdefault.llayer;

import com.hfernandes.springauthorizationserverdefault.config.oauth2.JpaRegisteredClientRepository;
import com.hfernandes.springauthorizationserverdefault.db.model.User;
import com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input.RegisterClientInputResource;
import com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input.RegisterUserInputResource;
import com.hfernandes.springauthorizationserverdefault.service.DBService;
import com.hfernandes.springauthorizationserverdefault.service.PasswordSecurityService;
import com.hfernandes.springauthorizationserverdefault.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class OAuthRegistrationAPIOperations {

     private static final Logger LOG = LoggerFactory.getLogger(OAuthRegistrationAPIOperations.class);

     @Autowired
     private JpaRegisteredClientRepository jpaRegisteredClientRepository;
     @Autowired
     private RandomService randomService;
     @Autowired
     private DBService dbService;
     @Autowired
     private PasswordSecurityService passwordSecurityService;


     public void registerOAuth2Client(RegisterClientInputResource registerClientInputResource) {
          RegisteredClient registeredClient = jpaRegisteredClientRepository.insertClient(registerClientInputResource.getClientId(), registerClientInputResource.getClientSecret());
     }

     public void registerUser(RegisterUserInputResource registerUserInputResource) {
          User user = new User("username", passwordSecurityService.encode("password"));
          dbService.insertUser(user);
     }
}
