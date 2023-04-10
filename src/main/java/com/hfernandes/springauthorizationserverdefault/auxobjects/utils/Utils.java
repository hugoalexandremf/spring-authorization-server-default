package com.hfernandes.springauthorizationserverdefault.auxobjects.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class Utils {

     private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

     public static String normalizeToLowerCase(String strToNormalize) {
          return strToNormalize.toLowerCase(Locale.ROOT);
     }
}
