package de.brifle.sdk.helper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Crypto {


    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static byte[] randomKey() throws NoSuchAlgorithmException {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(32);
        String randomString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        for (int i = 0; i < 32; i++) {
            int index = random.nextInt(randomString.length());
            sb.append(randomString.charAt(index));
        }
        // hash the random string and return
        messageDigest.update(sb.toString().getBytes());
        return messageDigest.digest();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    public static String hmac(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        final String algorithm = "HmacSHA256";
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }


}
