package com.hfernandes.springauthorizationserverdefault.controller.rest;

import com.hfernandes.springauthorizationserverdefault.llayer.OAuthRegistrationAPIOperations;
import com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input.RegisterClientInputResource;
import com.hfernandes.springauthorizationserverdefault.resource.oauthregistrationapiresource.input.RegisterUserInputResource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/oauthregistrationapi")
public class OAuthRegistrationAPIController {

     private static final Logger LOG = LoggerFactory.getLogger(OAuthRegistrationAPIController.class);

     @Autowired
     private OAuthRegistrationAPIOperations oAuthRegistrationAPIOperations;


     @RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public String health() {
          LOG.debug("/v1/oauthregistrationapi/health");

          String response = "{\"status\":\"ok\"}";

          LOG.debug("Return: " + response);
          return response;
     }

     @RequestMapping(value = "/registerClient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public void registerClient(@RequestBody @Valid RegisterClientInputResource registerClientInputResource) {
          LOG.info("/v1/oauthregistrationapi/registerClient");
          //LOG.info("Username: {}", principal.getName());
          LOG.info("RegisterClientInputResource: {}", registerClientInputResource);

          oAuthRegistrationAPIOperations.registerOAuth2Client(registerClientInputResource);

          LOG.info("Return: void");
     }

     @RequestMapping(value = "/registerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public void registerClient(@RequestBody @Valid RegisterUserInputResource registerUserInputResource) {
          LOG.info("/v1/oauthregistrationapi/registerUser");
          //LOG.info("Username: {}", principal.getName());
          LOG.info("RegisterUserInputResource: {}", registerUserInputResource);

          oAuthRegistrationAPIOperations.registerUser(registerUserInputResource);

          LOG.info("Return: void");
     }

}
