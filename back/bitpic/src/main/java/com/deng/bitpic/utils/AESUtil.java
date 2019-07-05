package com.deng.bitpic.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description: AES算法专用于购买凭证地址加解密
 * @author: Deng
 * @create: 2019-03-13
 */
@Slf4j
public class AESUtil {

    private static final String ENCRYPT_ALGORITHM = "AES";

    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";

    private static final String CHARACTER = "UTF-8";

    public static String encrypt(String text, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARACTER), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] bytes = cipher.doFinal(text.getBytes(CHARACTER));

            return new BASE64Encoder().encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String text, String key) {

        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] bytes = base64Decoder.decodeBuffer(text);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARACTER), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] bytes2 = cipher.doFinal(bytes);

            return new String(bytes2, CHARACTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
