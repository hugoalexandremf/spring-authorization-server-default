package com.hfernandes.springauthorizationserverdefault.config.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientRegistrationAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.oidc.http.converter.OidcClientRegistrationHttpMessageConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

public class CustomOidcClientRegistrationAuthenticationConverter implements AuthenticationConverter {

     private static final Logger LOG = LoggerFactory.getLogger(CustomOidcClientRegistrationAuthenticationConverter.class);

     private final HttpMessageConverter<OidcClientRegistration> clientRegistrationHttpMessageConverter =
             new OidcClientRegistrationHttpMessageConverter();

     @Override
     public Authentication convert(HttpServletRequest request) {
          Authentication principal = SecurityContextHolder.getContext().getAuthentication();

          LOG.info("principal:" + principal.getName());

          if ("POST".equals(request.getMethod())) {
               OidcClientRegistration clientRegistration;
               try {
                    clientRegistration = this.clientRegistrationHttpMessageConverter.read(
                            OidcClientRegistration.class, new ServletServerHttpRequest(request));
               } catch (Exception ex) {
                    LOG.info("exception:" + ex.getMessage());
                    OAuth2Error error = new OAuth2Error(
                            OAuth2ErrorCodes.INVALID_REQUEST,
                            "OpenID Client Registration Error: " + ex.getMessage(),
                            "https://openid.net/specs/openid-connect-registration-1_0.html#RegistrationError");
                    throw new OAuth2AuthenticationException(error, ex);
               }
               return new OidcClientRegistrationAuthenticationToken(principal, clientRegistration);
          }

          // client_id (REQUIRED)
          String clientId = request.getParameter(OAuth2ParameterNames.CLIENT_ID);
          if (!StringUtils.hasText(clientId) ||
                  request.getParameterValues(OAuth2ParameterNames.CLIENT_ID).length != 1) {
               throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
          }

          return new OidcClientRegistrationAuthenticationToken(principal, clientId);
     }

}