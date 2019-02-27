package animeApp.encryption;

/**
 * Created by admin on 3/31/2017.
 */
public interface ISymmetricEncryption {
    public void generateKey(int keySize);
    public byte[] encrypt(byte[] data);
    public byte[] decrypt(byte[] data);

}
