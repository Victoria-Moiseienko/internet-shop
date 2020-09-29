package com.internet.shop.util;

import com.internet.shop.exeptions.PasswordHashingException;
import com.internet.shop.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    public static final String SHA_512 = "SHA-512";

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_512);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashingException("Algorithm has not been found:/n", e);
        }
        return hashedPassword.toString();
    }

    public static boolean isValid(String psw, User user) {
        return hashPassword(psw, user.getSalt()).equals(user.getPassword());
    }
}
