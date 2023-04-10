package com.hfernandes.springauthorizationserverdefault.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(schema = "spring_authorization_server_default", name = "client")
public class Client implements Serializable {


     @Id
     private String id;
     @Column(name = "clientid")
     private String clientId;
     @Column(name = "clientidissuedat")
     private Instant clientIdIssuedAt;
     @Column(name = "clientsecret")
     private String clientSecret;
     @Column(name = "clientsecretexpiresat")
     private Instant clientSecretExpiresAt;
     @Column(name = "clientname")
     private String clientName;
     @Column(name = "clientauthenticationmethods", length = 1000)
     private String clientAuthenticationMethods;
     @Column(name = "authorizationgranttypes", length = 1000)
     private String authorizationGrantTypes;
     @Column(name = "redirecturis", length = 1000)
     private String redirectUris;
     @Column(name = "scopes", length = 1000)
     private String scopes;
     @Column(name = "clientsettings", length = 2000)
     private String clientSettings;
     @Column(name = "tokensettings", length = 2000)
     private String tokenSettings;


     public Client() {
     }


     public String getId() {
          return id;
     }

     public String getClientId() {
          return clientId;
     }

     public Instant getClientIdIssuedAt() {
          return clientIdIssuedAt;
     }

     public String getClientSecret() {
          return clientSecret;
     }

     public Instant getClientSecretExpiresAt() {
          return clientSecretExpiresAt;
     }

     public String getClientName() {
          return clientName;
     }

     public String getClientAuthenticationMethods() {
          return clientAuthenticationMethods;
     }

     public String getAuthorizationGrantTypes() {
          return authorizationGrantTypes;
     }

     public String getRedirectUris() {
          return redirectUris;
     }

     public String getScopes() {
          return scopes;
     }

     public String getClientSettings() {
          return clientSettings;
     }

     public String getTokenSettings() {
          return tokenSettings;
     }


     public void setId(String id) {
          this.id = id;
     }

     public void setClientId(String clientId) {
          this.clientId = clientId;
     }

     public void setClientIdIssuedAt(Instant clientIdIssuedAt) {
          this.clientIdIssuedAt = clientIdIssuedAt;
     }

     public void setClientSecret(String clientSecret) {
          this.clientSecret = clientSecret;
     }

     public void setClientSecretExpiresAt(Instant clientSecretExpiresAt) {
          this.clientSecretExpiresAt = clientSecretExpiresAt;
     }

     public void setClientName(String clientName) {
          this.clientName = clientName;
     }

     public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
          this.clientAuthenticationMethods = clientAuthenticationMethods;
     }

     public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
          this.authorizationGrantTypes = authorizationGrantTypes;
     }

     public void setRedirectUris(String redirectUris) {
          this.redirectUris = redirectUris;
     }

     public void setScopes(String scopes) {
          this.scopes = scopes;
     }

     public void setClientSettings(String clientSettings) {
          this.clientSettings = clientSettings;
     }

     public void setTokenSettings(String tokenSettings) {
          this.tokenSettings = tokenSettings;
     }


     @Override
     public String toString() {
          return "Client{" +
                  "id='" + id + '\'' +
                  ", clientId='" + clientId + '\'' +
                  ", clientIdIssuedAt=" + clientIdIssuedAt +
                  ", clientSecret='" + clientSecret + '\'' +
                  ", clientSecretExpiresAt=" + clientSecretExpiresAt +
                  ", clientName='" + clientName + '\'' +
                  ", clientAuthenticationMethods='" + clientAuthenticationMethods + '\'' +
                  ", authorizationGrantTypes='" + authorizationGrantTypes + '\'' +
                  ", redirectUris='" + redirectUris + '\'' +
                  ", scopes='" + scopes + '\'' +
                  ", clientSettings='" + clientSettings + '\'' +
                  ", tokenSettings='" + tokenSettings + '\'' +
                  '}';
     }

}