package com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input;

public class RegisterUserInputResource {


     private String username;
     private String password;


     public RegisterUserInputResource() {
     }


     public String getUsername() {
          return username;
     }

     public String getPassword() {
          return password;
     }


     public void setUsername(String username) {
          this.username = username;
     }

     public void setPassword(String password) {
          this.password = password;
     }


     @Override
     public String toString() {
          return "RegisterUserInputResource{" +
                  "username='" + username + '\'' +
                  ", password='" + password + '\'' +
                  '}';
     }
}
