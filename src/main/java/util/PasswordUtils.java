package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(salt);

            byte[] hash = md.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );

            return bytesToHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytesToHex(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }
}