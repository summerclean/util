package work.sihai.common.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {
    private static byte[] iv     = { 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 };

    /**
     *  加密算法
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes() , "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    /**
     * 解密算法
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes() , "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

    public static void main(String[] args) throws Exception {
        // 明文转密文
        String plaintext = "{ \"storeId\":\"8a9e48185b754539015b757e8acd000e\", \"channelCode\":\"BL0201\" } ";
        String ciphertext = DESUtil.encryptDES(plaintext, "123ABCde");
        System.out.println("密文：" + ciphertext);

        // 密文转明文
        String ciphertext2 = "yEtJYkqr3RCwASTljNVXHLhvNqFcqF7GUPwd6c0d91iZv6RS5xaGT6K9eR1k MSgmukVVr7QdN+mB4Vf9DFaI2Z2mup3yx9mGjnTTRNPWml8+Egv6lorY15AP S4U/pcLeqmjcRZUtfLQ=";
        System.out.println("解密后：" + DESUtil.decryptDES(ciphertext2, "123ABCde"));
    }
}
