package com.hfernandes.springauthorizationserverdefault.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/secureapi/v1")
public class APIRestController {

     private static final Logger LOG = LoggerFactory.getLogger(APIRestController.class);


     @RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public String health() {
          LOG.debug("/v1/mappapi/health");

          String response = "{\"status\":\"ok\"}";

          LOG.debug("Return: " + response);
          return response;
     }

     @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public void test(Principal principal) {
          LOG.info("/secureapi/v1/test");
          LOG.info("Username:{}", principal.getName());

          LOG.info("Return: void");
     }
}
