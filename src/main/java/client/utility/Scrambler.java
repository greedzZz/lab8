package client.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Scrambler {

    public byte[] getHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String pepper = "#7b№X2&m";
            return digest.digest((pepper + password).getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String getHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        String s;
        for (byte b : bytes) {
            s = Integer.toHexString(b);
            try {
                builder.append(s.substring(s.length() - 2));
            } catch (IndexOutOfBoundsException e) {
                builder.append("0").append(s);
            }
        }
        return builder.toString();
    }

    public String getPassword(String password) {
        return getHexString(getHash(password));
    }

}
