package com.hfernandes.springauthorizationserverdefault.controller.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MVCController {

     private static final Logger LOG = LoggerFactory.getLogger(MVCController.class);

     @RequestMapping(value = "/login")
     public String login() {
          LOG.info("/login");

          LOG.info("return:login");

          return "login";
     }

}
