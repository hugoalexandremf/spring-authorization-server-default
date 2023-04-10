package com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input;

public class RegisterClientInputResource {

     private String clientId;
     private String clientSecret;


     public RegisterClientInputResource() {
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
          return "RegisterClientInputResource{" +
                  "clientId='" + clientId + '\'' +
                  ", clientSecret='" + clientSecret + '\'' +
                  '}';
     }
}
