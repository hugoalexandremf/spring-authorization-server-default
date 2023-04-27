package com.hfernandes.springauthorizationserverdefault.config.oauth2.oicd;

import com.hfernandes.springauthorizationserverdefault.config.oauth2.CustomOidcClientRegistrationAuthenticationConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;

public final class CustomOidcUserInfoAuthenticationProvider implements AuthenticationProvider {
     private final Log logger = LogFactory.getLog(getClass());
     private final OAuth2AuthorizationService authorizationService;
     private Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper = new DefaultOidcUserInfoMapper();

     /**
      * Constructs an {@code OidcUserInfoAuthenticationProvider} using the provided parameters.
      *
      * @param authorizationService the authorization service
      */
     public CustomOidcUserInfoAuthenticationProvider(OAuth2AuthorizationService authorizationService) {
          Assert.notNull(authorizationService, "authorizationService cannot be null");
          this.authorizationService = authorizationService;
     }

     @Override
     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          logger.info("authenticate:" + authentication.getPrincipal().toString());
          
          OidcUserInfoAuthenticationToken userInfoAuthentication =
                  (OidcUserInfoAuthenticationToken) authentication;

          AbstractOAuth2TokenAuthenticationToken<?> accessTokenAuthentication = null;
          if (AbstractOAuth2TokenAuthenticationToken.class.isAssignableFrom(userInfoAuthentication.getPrincipal().getClass())) {
               accessTokenAuthentication = (AbstractOAuth2TokenAuthenticationToken<?>) userInfoAuthentication.getPrincipal();
          }
          if (accessTokenAuthentication == null || !accessTokenAuthentication.isAuthenticated()) {
               throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
          }

          String accessTokenValue = accessTokenAuthentication.getToken().getTokenValue();

          OAuth2Authorization authorization = this.authorizationService.findByToken(
                  accessTokenValue, OAuth2TokenType.ACCESS_TOKEN);
          if (authorization == null) {
               throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
          }

          if (this.logger.isTraceEnabled()) {
               this.logger.trace("Retrieved authorization with access token");
          }

          OAuth2Authorization.Token<OAuth2AccessToken> authorizedAccessToken = authorization.getAccessToken();
          if (!authorizedAccessToken.isActive()) {
               throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
          }

          if (!authorizedAccessToken.getToken().getScopes().contains(OidcScopes.OPENID)) {
               throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INSUFFICIENT_SCOPE);
          }

          OAuth2Authorization.Token<OidcIdToken> idToken = authorization.getToken(OidcIdToken.class);
          if (idToken == null) {
               throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
          }

          if (this.logger.isTraceEnabled()) {
               this.logger.trace("Validated user info request");
          }

          OidcUserInfoAuthenticationContext authenticationContext =
                  OidcUserInfoAuthenticationContext.with(userInfoAuthentication)
                          .accessToken(authorizedAccessToken.getToken())
                          .authorization(authorization)
                          .build();
          OidcUserInfo userInfo = this.userInfoMapper.apply(authenticationContext);

          if (this.logger.isTraceEnabled()) {
               this.logger.trace("Authenticated user info request");
          }

          return new OidcUserInfoAuthenticationToken(accessTokenAuthentication, userInfo);
     }

     @Override
     public boolean supports(Class<?> authentication) {
          return OidcUserInfoAuthenticationToken.class.isAssignableFrom(authentication);
     }

     /**
      * Sets the {@link Function} used to extract claims from {@link OidcUserInfoAuthenticationContext}
      * to an instance of {@link OidcUserInfo} for the UserInfo response.
      *
      * <p>
      * The {@link OidcUserInfoAuthenticationContext} gives the mapper access to the {@link OidcUserInfoAuthenticationToken},
      * as well as, the following context attributes:
      * <ul>
      * <li>{@link OidcUserInfoAuthenticationContext#getAccessToken()} containing the bearer token used to make the request.</li>
      * <li>{@link OidcUserInfoAuthenticationContext#getAuthorization()} containing the {@link OidcIdToken} and
      * {@link OAuth2AccessToken} associated with the bearer token used to make the request.</li>
      * </ul>
      *
      * @param userInfoMapper the {@link Function} used to extract claims from {@link OidcUserInfoAuthenticationContext} to an instance of {@link OidcUserInfo}
      */
     public void setUserInfoMapper(Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper) {
          Assert.notNull(userInfoMapper, "userInfoMapper cannot be null");
          this.userInfoMapper = userInfoMapper;
     }

     private static final class DefaultOidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {

          private static final List<String> EMAIL_CLAIMS = Arrays.asList(
                  StandardClaimNames.EMAIL,
                  StandardClaimNames.EMAIL_VERIFIED
          );
          private static final List<String> PHONE_CLAIMS = Arrays.asList(
                  StandardClaimNames.PHONE_NUMBER,
                  StandardClaimNames.PHONE_NUMBER_VERIFIED
          );
          private static final List<String> PROFILE_CLAIMS = Arrays.asList(
                  StandardClaimNames.NAME,
                  StandardClaimNames.FAMILY_NAME,
                  StandardClaimNames.GIVEN_NAME,
                  StandardClaimNames.MIDDLE_NAME,
                  StandardClaimNames.NICKNAME,
                  StandardClaimNames.PREFERRED_USERNAME,
                  StandardClaimNames.PROFILE,
                  StandardClaimNames.PICTURE,
                  StandardClaimNames.WEBSITE,
                  StandardClaimNames.GENDER,
                  StandardClaimNames.BIRTHDATE,
                  StandardClaimNames.ZONEINFO,
                  StandardClaimNames.LOCALE,
                  StandardClaimNames.UPDATED_AT
          );

          @Override
          public OidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
               OAuth2Authorization authorization = authenticationContext.getAuthorization();
               OidcIdToken idToken = authorization.getToken(OidcIdToken.class).getToken();
               OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
               Map<String, Object> scopeRequestedClaims = getClaimsRequestedByScope(idToken.getClaims(),
                       accessToken.getScopes());

               return new OidcUserInfo(scopeRequestedClaims);
          }

          private static Map<String, Object> getClaimsRequestedByScope(Map<String, Object> claims, Set<String> requestedScopes) {
               Set<String> scopeRequestedClaimNames = new HashSet<>(32);
               scopeRequestedClaimNames.add(StandardClaimNames.SUB);

               if (requestedScopes.contains(OidcScopes.ADDRESS)) {
                    scopeRequestedClaimNames.add(StandardClaimNames.ADDRESS);
               }
               if (requestedScopes.contains(OidcScopes.EMAIL)) {
                    scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
               }
               if (requestedScopes.contains(OidcScopes.PHONE)) {
                    scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
               }
               if (requestedScopes.contains(OidcScopes.PROFILE)) {
                    scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
               }

               Map<String, Object> requestedClaims = new HashMap<>(claims);
               requestedClaims.keySet().removeIf(claimName -> !scopeRequestedClaimNames.contains(claimName));

               return requestedClaims;
          }

     }

}