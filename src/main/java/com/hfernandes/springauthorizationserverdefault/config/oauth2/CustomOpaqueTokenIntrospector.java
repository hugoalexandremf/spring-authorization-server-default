package com.hfernandes.springauthorizationserverdefault.config.oauth2;

import com.hfernandes.springauthorizationserverdefault.db.model.User;
import com.hfernandes.springauthorizationserverdefault.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

     private static final Logger LOG = LoggerFactory.getLogger(CustomOpaqueTokenIntrospector.class);
     private static final String AUTHORITY_PREFIX = "SCOPE_";
     private static final String ISS = "ECMAM";
     private static final String DEVICE_ID = "device_id";

     @Autowired
     private JpaRegisteredClientRepository jpaRegisteredClientRepository;
     @Autowired
     private JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService;
     @Autowired
     private DBService dbService;


     @Override
     public OAuth2AuthenticatedPrincipal introspect(String token) {
          CustomOAuth2Authorization customOAuth2Authorization = jpaOAuth2AuthorizationService.getCustomOAuth2AuthorizationByAccessTokenValue(token);
          /*try {
               customOAuth2Authorization = jpaOAuth2AuthorizationService.findByToken(token);
          } catch (OAuth2AuthorizationNotFoundException e) {
               throw new InvalidTokenOAuth2AuthenticationException("OAuth2AuthorizationNotFoundException", e);
          }*/


          OAuth2Authorization oAuth2Authorization = customOAuth2Authorization.getoAuth2Authorization();
          RegisteredClient registeredClient = customOAuth2Authorization.getRegisteredClient();

          User user = dbService.getUserByUsername(registeredClient.getId());

          Collection<GrantedAuthority> authorities = new ArrayList<>();
          Map<String, Object> claims = new HashMap<>();

          claims.put(OAuth2TokenIntrospectionClaimNames.ACTIVE, user.getEnabled());
          claims.put(OAuth2TokenIntrospectionClaimNames.SUB, user.getUsername());

          claims.put(OAuth2TokenIntrospectionClaimNames.AUD, List.of(registeredClient.getClientName()));
          claims.put(OAuth2TokenIntrospectionClaimNames.CLIENT_ID, registeredClient.getClientName());
          claims.put(OAuth2TokenIntrospectionClaimNames.EXP, oAuth2Authorization.getAccessToken().getToken().getExpiresAt());
          claims.put(OAuth2TokenIntrospectionClaimNames.IAT, oAuth2Authorization.getAccessToken().getToken().getIssuedAt());
          claims.put(OAuth2TokenIntrospectionClaimNames.ISS, ISS);
          claims.put(OAuth2TokenIntrospectionClaimNames.NBF, oAuth2Authorization.getAccessToken().getToken().getIssuedAt());
          claims.put(DEVICE_ID, customOAuth2Authorization.getDeviceId());

          List<String> scopes = new ArrayList<>(oAuth2Authorization.getAuthorizedScopes());
          claims.put(OAuth2TokenIntrospectionClaimNames.SCOPE, scopes);

          for (String scope : scopes) {
               authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + scope));
          }

          return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
     }

}
