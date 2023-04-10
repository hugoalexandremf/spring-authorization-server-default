package com.hfernandes.springauthorizationserverdefault.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(schema = "spring_authorization_server_default", name = "authorizationconsent")
public class AuthorizationConsent implements Serializable {


     @EmbeddedId
     AuthorizationConsentId authorizationConsentId;
     @Column(name = "authorities", length = 1000)
     private String authorities;


     public AuthorizationConsent() {
     }


     public AuthorizationConsentId getAuthorizationConsentId() {
          return authorizationConsentId;
     }

     public String getAuthorities() {
          return authorities;
     }


     public void setAuthorizationConsentID(AuthorizationConsentId authorizationConsentId) {
          this.authorizationConsentId = authorizationConsentId;
     }

     public void setAuthorities(String authorities) {
          this.authorities = authorities;
     }


     @Override
     public String toString() {
          return "AuthorizationConsent{" +
                  "authorizationConsentId=" + authorizationConsentId +
                  ", authorities='" + authorities + '\'' +
                  '}';
     }

}