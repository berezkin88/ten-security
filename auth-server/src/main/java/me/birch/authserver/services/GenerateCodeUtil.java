package me.birch.authserver.services;

import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@UtilityClass
public class GenerateCodeUtil {

    @SuppressWarnings("java:S112")
    public static String generateCode() {
        try {
            var secureRandom = SecureRandom.getInstanceStrong();

            int code = secureRandom.nextInt(9000) + 1000;
            return String.valueOf(code);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code.", e);
        }
    }
}
