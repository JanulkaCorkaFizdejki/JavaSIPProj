package sipphone.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static void getMd5(String plaintext) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(plaintext));
        String s = String.format("%032x", new BigInteger(1, md5.digest()));
        System.out.println(s);
    }
}
