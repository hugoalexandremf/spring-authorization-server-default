package com.hfernandes.springauthorizationserverdefault.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Service
public class RandomService {

     private final SecureRandom secureRandom = new SecureRandom();

     private final String DIGITS = "0123456789";
     private final String CHARS = "abcdefghijklmnopqrstuvwxyz";

     public String generateDeviceID() {
          return generateRandomString(DIGITS.concat(CHARS), 36, secureRandom);
     }

     public String generateRequestID() {
          return generateRandomString(DIGITS.concat(CHARS), 32, secureRandom);
     }

     public String generateOTP() {
          return generateRandomString(DIGITS, 6, secureRandom);
     }

     public String generateDocID() {
          return generateRandomString(DIGITS.concat(CHARS), 36, secureRandom);
     }

     private static String generateRandomString(String chars, Integer length, Random random) {
          return random.ints(length, 0, chars.length()).mapToObj(chars::charAt).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
     }

     public byte[] getRandomBytes(int nBytes) {
          byte[] randomBytes = new byte[nBytes];

          secureRandom.nextBytes(randomBytes);

          return randomBytes;
     }

     public String generateUUID() {
          return UUID.randomUUID().toString();
     }

}
