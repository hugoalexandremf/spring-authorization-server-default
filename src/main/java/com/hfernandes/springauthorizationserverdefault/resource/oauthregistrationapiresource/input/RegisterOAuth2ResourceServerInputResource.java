package com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input;

public class RegisterOAuth2ResourceServerInputResource {

     private String clientId;
     private String clientSecret;

     public RegisterOAuth2ResourceServerInputResource() {
     }

     public String getClientId() {
          return clientId;
     }

     public String getClientSecret() {
          return clientSecret;
     }

     public void setClientId(String clientId) {
          this.clientId = clientId;
     }

     public void setClientSecret(String clientSecret) {
          this.clientSecret = clientSecret;
     }

     @Override
     public String toString() {
          return "RegisterOAuth2ResourceServerInputResource{" +
                  "clientId='" + clientId + '\'' +
                  ", clientSecret='" + clientSecret + '\'' +
                  '}';
     }
}
