package com.hfernandes.springauthorizationserverdefault.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(schema = "spring_authorization_server_default", name = "authorization")
public class Authorization implements Serializable {


     @Id
     @Column
     private String id;
     @Column(name = "registeredclientid")
     private String registeredClientId;
     @Column(name = "principalname")
     private String principalName;
     @Column(name = "authorizationgranttype")
     private String authorizationGrantType;
     @Column(name = "authorizedscopes", length = 1000)
     private String authorizedScopes;
     @Column(name = "attributes", length = 4000)
     private String attributes;
     @Column(name = "state", length = 500)
     private String state;

     @Column(name = "authorizationcodevalue", length = 4000)
     private String authorizationCodeValue;
     @Column(name = "authorizationcodeissuedat")
     private Instant authorizationCodeIssuedAt;
     @Column(name = "authorizationcodeexpiresat")
     private Instant authorizationCodeExpiresAt;
     @Column(name = "authorizationcodemetadata")
     private String authorizationCodeMetadata;

     @Column(name = "accesstokenvalue", length = 4000)
     private String accessTokenValue;
     @Column(name = "accesstokenissuedat")
     private Instant accessTokenIssuedAt;
     @Column(name = "accesstokenexpiresat")
     private Instant accessTokenExpiresAt;
     @Column(name = "accesstokenmetadata", length = 2000)
     private String accessTokenMetadata;
     @Column(name = "accesstokentype")
     private String accessTokenType;
     @Column(name = "accesstokenscopes", length = 1000)
     private String accessTokenScopes;

     @Column(name = "refreshtokenvalue", length = 4000)
     private String refreshTokenValue;
     @Column(name = "refreshtokenissuedat")
     private Instant refreshTokenIssuedAt;
     @Column(name = "refreshtokenexpiresat")
     private Instant refreshTokenExpiresAt;
     @Column(name = "refreshtokenmetadata", length = 2000)
     private String refreshTokenMetadata;

     @Column(name = "oidcidtokenvalue", length = 4000)
     private String oidcIdTokenValue;
     @Column(name = "oidcidtokenissuedat")
     private Instant oidcIdTokenIssuedAt;
     @Column(name = "oidcidtokenexpiresat")
     private Instant oidcIdTokenExpiresAt;
     @Column(name = "oidcidtokenmetadata", length = 2000)
     private String oidcIdTokenMetadata;
     @Column(name = "oidcidtokenclaims", length = 2000)
     private String oidcIdTokenClaims;

     //custom
     @Column(name = "deviceid", length = 36)
     private String deviceID;


     public Authorization() {
     }


     public String getId() {
          return id;
     }

     public String getRegisteredClientId() {
          return registeredClientId;
     }

     public String getPrincipalName() {
          return principalName;
     }

     public String getAuthorizationGrantType() {
          return authorizationGrantType;
     }

     public String getAuthorizedScopes() {
          return authorizedScopes;
     }

     public String getAttributes() {
          return attributes;
     }

     public String getState() {
          return state;
     }

     public String getAuthorizationCodeValue() {
          return authorizationCodeValue;
     }

     public Instant getAuthorizationCodeIssuedAt() {
          return authorizationCodeIssuedAt;
     }

     public Instant getAuthorizationCodeExpiresAt() {
          return authorizationCodeExpiresAt;
     }

     public String getAuthorizationCodeMetadata() {
          return authorizationCodeMetadata;
     }

     public String getAccessTokenValue() {
          return accessTokenValue;
     }

     public Instant getAccessTokenIssuedAt() {
          return accessTokenIssuedAt;
     }

     public Instant getAccessTokenExpiresAt() {
          return accessTokenExpiresAt;
     }

     public String getAccessTokenMetadata() {
          return accessTokenMetadata;
     }

     public String getAccessTokenType() {
          return accessTokenType;
     }

     public String getAccessTokenScopes() {
          return accessTokenScopes;
     }

     public String getRefreshTokenValue() {
          return refreshTokenValue;
     }

     public Instant getRefreshTokenIssuedAt() {
          return refreshTokenIssuedAt;
     }

     public Instant getRefreshTokenExpiresAt() {
          return refreshTokenExpiresAt;
     }

     public String getRefreshTokenMetadata() {
          return refreshTokenMetadata;
     }

     public String getOidcIdTokenValue() {
          return oidcIdTokenValue;
     }

     public Instant getOidcIdTokenIssuedAt() {
          return oidcIdTokenIssuedAt;
     }

     public Instant getOidcIdTokenExpiresAt() {
          return oidcIdTokenExpiresAt;
     }

     public String getOidcIdTokenMetadata() {
          return oidcIdTokenMetadata;
     }

     public String getOidcIdTokenClaims() {
          return oidcIdTokenClaims;
     }

     public String getDeviceID() {
          return deviceID;
     }


     public void setId(String id) {
          this.id = id;
     }

     public void setRegisteredClientId(String registeredClientId) {
          this.registeredClientId = registeredClientId;
     }

     public void setPrincipalName(String principalName) {
          this.principalName = principalName;
     }

     public void setAuthorizationGrantType(String authorizationGrantType) {
          this.authorizationGrantType = authorizationGrantType;
     }

     public void setAuthorizedScopes(String authorizedScopes) {
          this.authorizedScopes = authorizedScopes;
     }

     public void setAttributes(String attributes) {
          this.attributes = attributes;
     }

     public void setState(String state) {
          this.state = state;
     }

     public void setAuthorizationCodeValue(String authorizationCodeValue) {
          this.authorizationCodeValue = authorizationCodeValue;
     }

     public void setAuthorizationCodeIssuedAt(Instant authorizationCodeIssuedAt) {
          this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
     }

     public void setAuthorizationCodeExpiresAt(Instant authorizationCodeExpiresAt) {
          this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
     }

     public void setAuthorizationCodeMetadata(String authorizationCodeMetadata) {
          this.authorizationCodeMetadata = authorizationCodeMetadata;
     }

     public void setAccessTokenValue(String accessTokenValue) {
          this.accessTokenValue = accessTokenValue;
     }

     public void setAccessTokenIssuedAt(Instant accessTokenIssuedAt) {
          this.accessTokenIssuedAt = accessTokenIssuedAt;
     }

     public void setAccessTokenExpiresAt(Instant accessTokenExpiresAt) {
          this.accessTokenExpiresAt = accessTokenExpiresAt;
     }

     public void setAccessTokenMetadata(String accessTokenMetadata) {
          this.accessTokenMetadata = accessTokenMetadata;
     }

     public void setAccessTokenType(String accessTokenType) {
          this.accessTokenType = accessTokenType;
     }

     public void setAccessTokenScopes(String accessTokenScopes) {
          this.accessTokenScopes = accessTokenScopes;
     }

     public void setRefreshTokenValue(String refreshTokenValue) {
          this.refreshTokenValue = refreshTokenValue;
     }

     public void setRefreshTokenIssuedAt(Instant refreshTokenIssuedAt) {
          this.refreshTokenIssuedAt = refreshTokenIssuedAt;
     }

     public void setRefreshTokenExpiresAt(Instant refreshTokenExpiresAt) {
          this.refreshTokenExpiresAt = refreshTokenExpiresAt;
     }

     public void setRefreshTokenMetadata(String refreshTokenMetadata) {
          this.refreshTokenMetadata = refreshTokenMetadata;
     }

     public void setOidcIdTokenValue(String oidcIdTokenValue) {
          this.oidcIdTokenValue = oidcIdTokenValue;
     }

     public void setOidcIdTokenIssuedAt(Instant oidcIdTokenIssuedAt) {
          this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
     }

     public void setOidcIdTokenExpiresAt(Instant oidcIdTokenExpiresAt) {
          this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
     }

     public void setOidcIdTokenMetadata(String oidcIdTokenMetadata) {
          this.oidcIdTokenMetadata = oidcIdTokenMetadata;
     }

     public void setOidcIdTokenClaims(String oidcIdTokenClaims) {
          this.oidcIdTokenClaims = oidcIdTokenClaims;
     }

     public void setDeviceID(String deviceID) {
          this.deviceID = deviceID;
     }


     /*@Override
     public String toString() {
          return "Authorization{" +
                  "id='" + id + '\'' +
                  ", registeredClientId='" + registeredClientId + '\'' +
                  ", principalName='" + principalName + '\'' +
                  ", authorizationGrantType='" + authorizationGrantType + '\'' +
                  ", authorizedScopes='" + authorizedScopes + '\'' +
                  ", attributes='" + attributes + '\'' +
                  ", state='" + state + '\'' +
                  ", authorizationCodeValue='" + authorizationCodeValue + '\'' +
                  ", authorizationCodeIssuedAt=" + authorizationCodeIssuedAt +
                  ", authorizationCodeExpiresAt=" + authorizationCodeExpiresAt +
                  ", authorizationCodeMetadata='" + authorizationCodeMetadata + '\'' +
                  ", accessTokenValue='" + accessTokenValue + '\'' +
                  ", accessTokenIssuedAt=" + accessTokenIssuedAt +
                  ", accessTokenExpiresAt=" + accessTokenExpiresAt +
                  ", accessTokenMetadata='" + accessTokenMetadata + '\'' +
                  ", accessTokenType='" + accessTokenType + '\'' +
                  ", accessTokenScopes='" + accessTokenScopes + '\'' +
                  ", refreshTokenValue='" + refreshTokenValue + '\'' +
                  ", refreshTokenIssuedAt=" + refreshTokenIssuedAt +
                  ", refreshTokenExpiresAt=" + refreshTokenExpiresAt +
                  ", refreshTokenMetadata='" + refreshTokenMetadata + '\'' +
                  ", oidcIdTokenValue='" + oidcIdTokenValue + '\'' +
                  ", oidcIdTokenIssuedAt=" + oidcIdTokenIssuedAt +
                  ", oidcIdTokenExpiresAt=" + oidcIdTokenExpiresAt +
                  ", oidcIdTokenMetadata='" + oidcIdTokenMetadata + '\'' +
                  ", oidcIdTokenClaims='" + oidcIdTokenClaims + '\'' +
                  '}';
     }*/

     @Override
     public String toString() {
          return "Authorization{" +
                  "id='" + id + '\'' +
                  ", registeredClientId='" + registeredClientId + '\'' +
                  ", principalName='" + principalName + '\'' +
                  ", authorizationGrantType='" + authorizationGrantType + '\'' +
                  ", authorizedScopes='" + authorizedScopes + '\'' +
                  ", attributes='" + attributes + '\'' +
                  ", state='" + state + '\'' +
                  ", authorizationCodeValue='" + authorizationCodeValue + '\'' +
                  ", authorizationCodeIssuedAt=" + authorizationCodeIssuedAt +
                  ", authorizationCodeExpiresAt=" + authorizationCodeExpiresAt +
                  ", authorizationCodeMetadata='" + authorizationCodeMetadata + '\'' +
                  ", accessTokenValue='" + accessTokenValue + '\'' +
                  ", accessTokenIssuedAt=" + accessTokenIssuedAt +
                  ", accessTokenExpiresAt=" + accessTokenExpiresAt +
                  ", accessTokenMetadata='" + accessTokenMetadata + '\'' +
                  ", accessTokenType='" + accessTokenType + '\'' +
                  ", accessTokenScopes='" + accessTokenScopes + '\'' +
                  ", refreshTokenValue='" + refreshTokenValue + '\'' +
                  ", refreshTokenIssuedAt=" + refreshTokenIssuedAt +
                  ", refreshTokenExpiresAt=" + refreshTokenExpiresAt +
                  ", refreshTokenMetadata='" + refreshTokenMetadata + '\'' +
                  ", oidcIdTokenValue='" + oidcIdTokenValue + '\'' +
                  ", oidcIdTokenIssuedAt=" + oidcIdTokenIssuedAt +
                  ", oidcIdTokenExpiresAt=" + oidcIdTokenExpiresAt +
                  ", oidcIdTokenMetadata='" + oidcIdTokenMetadata + '\'' +
                  ", oidcIdTokenClaims='" + oidcIdTokenClaims + '\'' +
                  ", deviceID='" + deviceID + '\'' +
                  '}';
     }

}