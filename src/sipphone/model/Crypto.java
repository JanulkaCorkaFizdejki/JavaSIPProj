package sipphone.model;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * This class provides methods to encrypt and decrypt data.
 * @author Stephane Moreau
 */
public class Crypto {

    private byte[] toByteArray (String s) {
        return s.getBytes();
    }

    private static String md5(final String input) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] messageDigest = md.digest(input.getBytes());
        final BigInteger number = new BigInteger(1, messageDigest);
        return String.format("%032x", number);
    }

    private Cipher initCipher(final int mode, final String initialVectorString, final String secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        final SecretKeySpec skeySpec = new SecretKeySpec(toByteArray(md5(secretKey)), "AES");
        final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
        final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(mode, skeySpec, initialVector);
        return cipher;
    }

    public String encrypt(final String dataToEncrypt, final String initialVector, final String secretKey) {
        String encryptedData = null;
        try {
            // Initialize the cipher
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, initialVector, secretKey);
            // Encrypt the data
            final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt.getBytes());
            // Encode using Base64
            encryptedData = String.valueOf(Base64.getEncoder().encode(encryptedByteArray));
        } catch (Exception e) {
            System.err.println("Problem encrypting the data");
            e.printStackTrace();
        }
        return encryptedData;
    }

    public String decrypt(final String encryptedData, final String initialVector, final String secretKey) {
        String decryptedData = null;
        try {
            // Initialize the cipher
            final Cipher cipher = initCipher(Cipher.DECRYPT_MODE, initialVector, secretKey);
           final byte [] encryptedByteArray = Base64.getMimeDecoder().decode(encryptedData);
           final byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
           decryptedData = new String(decryptedByteArray, "UTF8");
        } catch (Exception e) {
            System.err.println("Problem decrypting the data x x x");
            e.printStackTrace();
        }
        return decryptedData;
    }
}