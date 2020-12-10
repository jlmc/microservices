package io.github.jlmc.chassis.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Encryption(type = Encryption.Type.MD5)
public class MD5Encryptor implements Encryptor {

    @Override
    public String hash(String input) {
        byte[] bytes = toMd5(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(bytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private byte[] toMd5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
