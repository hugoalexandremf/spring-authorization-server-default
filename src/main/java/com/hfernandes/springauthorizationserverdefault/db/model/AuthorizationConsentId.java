package com.hfernandes.springauthorizationserverdefault.db.model;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class AuthorizationConsentId implements Serializable {

     @Column(name = "registeredclientid")
     private String registeredClientId;
     @Column(name = "principalname")
     private String principalName;


     protected AuthorizationConsentId() {
     }


     public String getRegisteredClientId() {
          return registeredClientId;
     }

     public String getPrincipalName() {
          return principalName;
     }


     public void setRegisteredClientId(String registeredClientId) {
          this.registeredClientId = registeredClientId;
     }

     public void setPrincipalName(String principalName) {
          this.principalName = principalName;
     }


     @Override
     public String toString() {
          return "AuthorizationConsentId{" +
                  "registeredClientId='" + registeredClientId + '\'' +
                  ", principalName='" + principalName + '\'' +
                  '}';
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          AuthorizationConsentId that = (AuthorizationConsentId) o;
          return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
     }

     @Override
     public int hashCode() {
          return Objects.hash(registeredClientId, principalName);
     }

}