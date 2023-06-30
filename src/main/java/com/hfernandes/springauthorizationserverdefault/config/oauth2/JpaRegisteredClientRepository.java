package com.hfernandes.springauthorizationserverdefault.config.oauth2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfernandes.springauthorizationserverdefault.auxobjects.utils.Utils;
import com.hfernandes.springauthorizationserverdefault.db.model.Client;
import com.hfernandes.springauthorizationserverdefault.db.repository.ClientRepository;
import com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input.RegisterOAuth2ResourceServerInputResource;
import com.hfernandes.springauthorizationserverdefault.service.PasswordSecurityService;
import com.hfernandes.springauthorizationserverdefault.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

     private final ClientRepository clientRepository;
     private final ObjectMapper objectMapper = new ObjectMapper();

     //custom
     private static final String SCOPE_SECUREAPI = "secureapi";
     @Autowired
     private RandomService randomService;
     @Autowired
     private PasswordSecurityService passwordSecurityService;

     public JpaRegisteredClientRepository(ClientRepository clientRepository) {
          Assert.notNull(clientRepository, "clientRepository cannot be null");
          this.clientRepository = clientRepository;

          ClassLoader classLoader = JpaRegisteredClientRepository.class.getClassLoader();
          List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
          this.objectMapper.registerModules(securityModules);
          this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
     }

     @Override
     public void save(RegisteredClient registeredClient) {
          Assert.notNull(registeredClient, "registeredClient cannot be null");
          this.clientRepository.save(toEntity(registeredClient));
     }

     @Override
     public RegisteredClient findById(String id) {
          Assert.hasText(id, "id cannot be empty");
          return this.clientRepository.findById(id).map(this::toObject).orElse(null);
     }

     @Override
     public RegisteredClient findByClientId(String clientId) {
          Assert.hasText(clientId, "clientId cannot be empty");
          return this.clientRepository.findByClientId(clientId).map(this::toObject).orElse(null);
     }

     private RegisteredClient toObject(Client client) {
          Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(
                  client.getClientAuthenticationMethods());
          Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(
                  client.getAuthorizationGrantTypes());
          Set<String> redirectUris = StringUtils.commaDelimitedListToSet(
                  client.getRedirectUris());
          Set<String> clientScopes = StringUtils.commaDelimitedListToSet(
                  client.getScopes());

          RegisteredClient.Builder builder = RegisteredClient.withId(client.getId())
                  .clientId(client.getClientId())
                  .clientIdIssuedAt(client.getClientIdIssuedAt())
                  .clientSecret(client.getClientSecret())
                  .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                  .clientName(client.getClientName())
                  .clientAuthenticationMethods(authenticationMethods ->
                          clientAuthenticationMethods.forEach(authenticationMethod ->
                                  authenticationMethods.add(resolveClientAuthenticationMethod(authenticationMethod))))
                  .authorizationGrantTypes((grantTypes) ->
                          authorizationGrantTypes.forEach(grantType ->
                                  grantTypes.add(resolveAuthorizationGrantType(grantType))))
                  .redirectUris((uris) -> uris.addAll(redirectUris))
                  .scopes((scopes) -> scopes.addAll(clientScopes));

          Map<String, Object> clientSettingsMap = parseMap(client.getClientSettings());
          builder.clientSettings(ClientSettings.withSettings(clientSettingsMap).build());

          Map<String, Object> tokenSettingsMap = parseMap(client.getTokenSettings());
          builder.tokenSettings(TokenSettings.withSettings(tokenSettingsMap).build());

          return builder.build();
     }

     private Client toEntity(RegisteredClient registeredClient) {
          List<String> clientAuthenticationMethods = new ArrayList<>(registeredClient.getClientAuthenticationMethods().size());
          registeredClient.getClientAuthenticationMethods().forEach(clientAuthenticationMethod ->
                  clientAuthenticationMethods.add(clientAuthenticationMethod.getValue()));

          List<String> authorizationGrantTypes = new ArrayList<>(registeredClient.getAuthorizationGrantTypes().size());
          registeredClient.getAuthorizationGrantTypes().forEach(authorizationGrantType ->
                  authorizationGrantTypes.add(authorizationGrantType.getValue()));

          Client entity = new Client();
          entity.setId(registeredClient.getId());
          entity.setClientId(registeredClient.getClientId());
          entity.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
          entity.setClientSecret(registeredClient.getClientSecret());
          entity.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
          entity.setClientName(registeredClient.getClientName());
          entity.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods));
          entity.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes));
          entity.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
          entity.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));
          entity.setClientSettings(writeMap(registeredClient.getClientSettings().getSettings()));
          entity.setTokenSettings(writeMap(registeredClient.getTokenSettings().getSettings()));

          return entity;
     }

     private Map<String, Object> parseMap(String data) {
          try {
               return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
               });
          } catch (Exception ex) {
               throw new IllegalArgumentException(ex.getMessage(), ex);
          }
     }

     private String writeMap(Map<String, Object> data) {
          try {
               return this.objectMapper.writeValueAsString(data);
          } catch (Exception ex) {
               throw new IllegalArgumentException(ex.getMessage(), ex);
          }
     }

     private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
          if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
               return AuthorizationGrantType.AUTHORIZATION_CODE;
          } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
               return AuthorizationGrantType.CLIENT_CREDENTIALS;
          } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
               return AuthorizationGrantType.REFRESH_TOKEN;
          }
          return new AuthorizationGrantType(authorizationGrantType);              // Custom authorization grant type
     }

     private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
          if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
               return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
          } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
               return ClientAuthenticationMethod.CLIENT_SECRET_POST;
          } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
               return ClientAuthenticationMethod.NONE;
          }
          return new ClientAuthenticationMethod(clientAuthenticationMethod);      // Custom client authentication method
     }

     //custom

     public Optional<RegisteredClient> getOptionalRegisteredClientById(String id) {
          Assert.hasText(id, "id cannot be empty");
          return this.clientRepository.findById(id).map(this::toObject);
     }

     public RegisteredClient insertClient(String clientId, String clientSecret) {
          String registeredClientID;
          Optional<RegisteredClient> optionalRegisteredClient;
          do {
               registeredClientID = randomService.generateUUID();
               optionalRegisteredClient = getOptionalRegisteredClientById(registeredClientID);
          } while (optionalRegisteredClient.isPresent());

          RegisteredClient registeredClient = RegisteredClient.withId(registeredClientID)
                  .clientId(Utils.normalizeToLowerCase(clientId))
                  .clientName(Utils.normalizeToLowerCase(clientId))
                  .clientIdIssuedAt(Instant.now())
                  .clientSecret(passwordSecurityService.encode(clientSecret))
                  //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                  //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                  .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                  .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                  .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                  //.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                  //.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                  //.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                  .redirectUri("http://127.0.0.1:3000/api/auth/callback")
                  /*.redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                  .redirectUri("http://127.0.0.1:8080/authorized")
                  .scope(OidcScopes.OPENID)
                  .scope(OidcScopes.PROFILE)
                  .scope("message.read")
                  .scope("message.write")*/
                  .scope(OidcScopes.OPENID)
                  .scope(SCOPE_SECUREAPI)
                  .clientSettings(ClientSettings.builder()
                          .requireAuthorizationConsent(false)
                          // proof key code exchange - pkce
                          .requireProofKey(true)
                          .build())
                  .tokenSettings(TokenSettings.builder()
                          .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                          .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                          .accessTokenTimeToLive(Duration.ofDays(7))
                          .refreshTokenTimeToLive(Duration.ofDays(14))
                          .build())
                  .build();

          this.save(registeredClient);

          return registeredClient;
     }

     public RegisteredClient insertResourceServerClient(RegisterOAuth2ResourceServerInputResource registerOAuth2ResourceServerInputResource) {
          String registeredClientID;
          Optional<RegisteredClient> optionalRegisteredClient;
          do {
               registeredClientID = randomService.generateUUID();
               optionalRegisteredClient = getOptionalRegisteredClientById(registeredClientID);
          } while (optionalRegisteredClient.isPresent());

          RegisteredClient registeredClient = RegisteredClient.withId(registeredClientID)
                  .clientId(Utils.normalizeToLowerCase(registerOAuth2ResourceServerInputResource.getClientId()))
                  .clientName(Utils.normalizeToLowerCase(registerOAuth2ResourceServerInputResource.getClientId()))
                  .clientIdIssuedAt(Instant.now())
                  .clientSecret(passwordSecurityService.encode(registerOAuth2ResourceServerInputResource.getClientSecret()))
                  //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                  //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                  .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                  .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                  .scope(OidcScopes.OPENID)
                  .scope(SCOPE_SECUREAPI)
                  .clientSettings(ClientSettings.builder()
                          .requireAuthorizationConsent(false)
                          .build())
                  .tokenSettings(TokenSettings.builder()
                          .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                          .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                          .accessTokenTimeToLive(Duration.ofDays(7))
                          .refreshTokenTimeToLive(Duration.ofDays(14))
                          .build())
                  .build();

          this.save(registeredClient);

          return registeredClient;
     }

}
