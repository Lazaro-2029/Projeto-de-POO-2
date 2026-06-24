package br.org.editora.model.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class SenhaUtil {

    private SenhaUtil() {}

    public static String hash(String senhaPlana) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senhaPlana.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 não disponível", e);
        }
    }

    public static boolean verificar(String senhaPlana, String hashArmazenado) {
        return hash(senhaPlana).equals(hashArmazenado);
    }
}
