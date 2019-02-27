package animeApp.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by admin on 3/30/2017.
 */
public class AES implements ISymmetricEncryption {
    private SecretKey key;
    private static AES aesInstance;

    public static AES getInstance(){
        synchronized (AES.class){
            if(aesInstance==null){
                aesInstance = new AES();
            }
            return aesInstance;
        }
    }

    private AES(){
        int maxKeyLength = 128;
        try {
            int temp = Cipher.getMaxAllowedKeyLength("AES");
            if(temp >= 256){
                maxKeyLength = 256;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generateKey(maxKeyLength);
    }

    public String getKey(){
        return Base64.getEncoder().encodeToString(this.key.getEncoded());
    }

    public void setKey(String key){
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.key = new SecretKeySpec(decodedKey, 0 ,decodedKey.length, "AES");
    }

    @Override
    public void generateKey(int keySize) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(keySize);
            this.key = keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param data
     * @param action True to encrypt or false to decrypt
     * @return
     */
    private byte[] encryptORdecrypt(byte[] data, boolean action){
        byte[] result = new byte[0];
        try {
            //AES/ECB/PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            if(action)
                cipher.init(Cipher.ENCRYPT_MODE, this.key);
            else
                cipher.init(Cipher.DECRYPT_MODE, this.key);

            result = cipher.doFinal(data);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return encryptORdecrypt(data,true);
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return encryptORdecrypt(data,false);
    }
}
