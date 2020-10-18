package sample.logic;

import org.apache.commons.codec.binary.Base64;
import sample.Controller;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Logic {
    private static final String ALGORITHM = "AES";

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(Controller.mainPassword.getBytes(), ALGORITHM);
        return key;
    }

    public String encrypt(String valueToEnc) {
        Key key = null;
        try {
            key = generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            c.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] encValue = new byte[0];
        try {
            encValue = c.doFinal(valueToEnc.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        String encryptedValue = new String(Base64.encodeBase64(encValue));
        return encryptedValue;
    }

    public String decrypt(String encryptedValue) {
        Key key = null;
        try {
            key = generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            c.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] decodedValue = Base64.decodeBase64(encryptedValue.getBytes());
        byte[] decValue = new byte[0];
        try {
            decValue = c.doFinal(decodedValue);
            Controller.isCorrectPassword = true;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Controller.isCorrectPassword = false;
        }
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
}
