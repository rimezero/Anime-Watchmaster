package animeApp.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by admin on 3/30/2017.
 */
public class RSA {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private static RSA rsaInstance;

    public static RSA getInstance(){
        synchronized (RSA.class){
            if(rsaInstance == null) {
                rsaInstance = new RSA();
            }
            return rsaInstance;
        }
    }

    private RSA(){
        generateKey(2048);
        setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArWPzTAQMGe55MtjxwZhqH9q6sBqKSDeLjvoNY5wAsUN/XorSkoZjv9eWtTpDZRdSEUnJQC+jd77/3CfEO1Unhye7wGaCb/WE5y8pvhHEJPP2MnOlcB2dHnWKQ+kWJtmh99P0pBpMhm4h9IKbwtmt2uihRBWPxHaqirdefG/R1hDnohzBHc8oCAq9ZiVPsICM5lmi7HSRQEgedkLA9ZBqr2Z+E7Yy6aiutFwHfXZ6qaCtoSfPcruyyq97FVGEK14cWud5TdCRWKaot2a6hHprVf//VilSks1Np/Sk66DO4xt6Q8vf2cLVEyJSrCBSy3Aw1HWUjgbyBQbe14Uy+d6mnwIDAQAB");
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPublicKey_String(){
        return Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPublicKey(String publicKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] decodedKey = Base64.getDecoder().decode(publicKey);
            //keyFactory.generatePublic(new SecretKeySpec(decodedKey, 0, decodedKey.length, "RSA"));
            this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getPrivateKey_String(){
        return Base64.getEncoder().encodeToString(this.privateKey.getEncoded());
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPrivateKey(String privateKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] decodedKey = Base64.getDecoder().decode(privateKey);
            //keyFactory.generatePublic(new SecretKeySpec(decodedKey, 0, decodedKey.length, "RSA"));
            this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    public void generateKey(int keySize) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keySize);
            KeyPair keyPair = kpg.generateKeyPair();
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param data
     * @param keyType
     * @param action true to encrypt or false to decrypt
     * @return
     */
    private byte[] encryptORdecrypt(byte[] data, boolean keyType, int action){
        byte[] result = new byte[0];

        try {
            //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            if(keyType)
                cipher.init(action, this.publicKey);
            else
                cipher.init(action, this.privateKey);

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

    /**
     *
     * @param data
     * @param keyType True for public key or false for private key
     * @return
     */
    public byte[] encrypt(byte[] data, boolean keyType) {
        return encryptORdecrypt(data,keyType,Cipher.ENCRYPT_MODE);
    }

    public byte[] decrypt(byte[] data, boolean keyType) {
        return encryptORdecrypt(data,keyType,Cipher.DECRYPT_MODE);
    }
}
