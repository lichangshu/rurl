/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 *
 * @author changshu.li
 */
public class DataEncrypt {

    public static final String ALGORITHM = "DES";

    private DataEncrypt() {
    }

    private static Key toDesKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码  
        // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);  
        return secretKey;
    }

    public static byte[] desDecrypt(byte[] data, String key) throws Exception {
        Key k = toDesKey(decodeBase64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static byte[] desEncrypt(byte[] data, String key) throws Exception {
        Key k = toDesKey(decodeBase64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * BASE64 解码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decodeBase64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64 编码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodeBase64(byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String md5sixteen(String str) {
        return md5(str).substring(8, 24);
    }

    public static String aesEncrypt(String dta, String key, String iv) throws Exception {
        return encodeBase64(aesEncrypt(dta.getBytes("UTF-8"), key, iv));
    }

    public static byte[] aesEncrypt(byte[] dta, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();

        int plaintextLength = dta.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dta, 0, plaintext, 0, dta.length);

        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("UTF-8"));

        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        return cipher.doFinal(plaintext);
    }

    public static String aesDecrypt(String data, String key, String iv) throws Exception {
        return new String(aesDecrypt(decodeBase64(data), key, iv), "UTF-8");
    }

    public static byte[] aesDecrypt(byte[] decode, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        return cipher.doFinal(decode);
    }

    public static void main(String[] args) throws Exception {
        String data = "Test 中国 String";
        String key = "G@bWGA~o,xt.u2a1";
        String iv = "G@bWGA~o,xt.u2a1";
        System.out.println((aesEncrypt(data, key, iv)));
        System.out.println(aesDecrypt("VKsMmExtcX8NeJ7f8gt2zWsvKbvgdG1vFSk6HlllKNk=", key, iv));
    }
}
