package hashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD2 {
    public static String apply(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");

            // calculating hash
            byte[] messageDigest = md.digest(input.getBytes());

            // converting hash to BigInteger
            BigInteger no = new BigInteger(1, messageDigest);

            // converting BigInteger to string as hex number
            String hashtext = no.toString(16);

            // align to 32 symbols
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
