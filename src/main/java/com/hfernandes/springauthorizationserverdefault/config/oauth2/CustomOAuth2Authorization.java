package com.hfernandes.springauthorizationserverdefault.config.oauth2;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public class CustomOAuth2Authorization {
     private OAuth2Authorization oAuth2Authorization;
     private String deviceId;
     private RegisteredClient registeredClient;


     public CustomOAuth2Authorization(OAuth2Authorization oAuth2Authorization, String deviceID) {
          this.oAuth2Authorization = oAuth2Authorization;
          this.deviceId = deviceID;
     }

     public CustomOAuth2Authorization(OAuth2Authorization oAuth2Authorization, String deviceID, RegisteredClient registeredClient) {
          this.oAuth2Authorization = oAuth2Authorization;
          this.deviceId = deviceID;
          this.registeredClient = registeredClient;
     }


     public OAuth2Authorization getoAuth2Authorization() {
          return oAuth2Authorization;
     }

     public String getDeviceId() {
          return deviceId;
     }

     public RegisteredClient getRegisteredClient() {
          return registeredClient;
     }


     public void setoAuth2Authorization(OAuth2Authorization oAuth2Authorization) {
          this.oAuth2Authorization = oAuth2Authorization;
     }

     public void setDeviceId(String deviceId) {
          this.deviceId = deviceId;
     }

     public void setRegisteredClient(RegisteredClient registeredClient) {
          this.registeredClient = registeredClient;
     }


     @Override
     public String toString() {
          return "CustomOAuth2Authorization{" +
                  "oAuth2Authorization=" + oAuth2Authorization +
                  ", deviceId='" + deviceId + '\'' +
                  ", registeredClient=" + registeredClient +
                  '}';
     }

}
