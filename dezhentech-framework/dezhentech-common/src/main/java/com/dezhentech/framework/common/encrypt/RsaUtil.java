package com.dezhentech.framework.common.encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;


/**
 * @description: 非对称加密工具
 * @title: com.dezhentech.common.encrypt.RsaUtil
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/24 11:07:40
 * @version: 1.0.0
 **/
public class RsaUtil {


    /**
     * @return {@code RsaKeyPair }
     * @descriptions 创建一个RSA的公钥私钥对
     * @author yingfan.cui@dezhentech.com
     * @time 2022/10/24 11:08:35
     */
    public static RsaKeyPair createKeyPair() {
        RSA rsa = SecureUtil.rsa();
        return new RsaKeyPair(rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64());
    }


    /**
     * @param publicKey 公钥
     * @param plaintext 明文
     * @return {@code String }
     * @descriptions 公钥加密
     * @author yingfan.cui@dezhentech.com
     * @time 2022/10/24 11:12:39
     */
    public static String encryptByPublicKey(String publicKey, String plaintext) {
        RSA rsa = SecureUtil.rsa(null, publicKey);
        return rsa.encryptBase64(plaintext, KeyType.PublicKey);
    }


    /**
     * @param privateKey 私钥
     * @param ciphertext 密文
     * @return {@code String }
     * @descriptions 由私钥解密
     * @author yingfan.cui@dezhentech.com
     * @time 2022/10/24 11:14:03
     */
    public static String decryptByPrivateKey(String privateKey, String ciphertext) {
        RSA rsa = SecureUtil.rsa(privateKey, null);
        return rsa.decryptStr(ciphertext, KeyType.PrivateKey);
    }


    /**
     * @param privateKey 私钥
     * @param plaintext  明文
     * @return {@code String }
     * @descriptions 由私钥加密
     * @author yingfan.cui@dezhentech.com
     * @time 2022/10/24 11:15:37
     */
    public static String encryptByPrivateKey(String privateKey, String plaintext) {
        RSA rsa = SecureUtil.rsa(privateKey, null);
        return rsa.encryptBase64(plaintext, KeyType.PrivateKey);
    }


    /**
     * @param publicKey  公钥
     * @param ciphertext 密文
     * @return {@code String }
     * @descriptions 由公钥解密
     * @author yingfan.cui@dezhentech.com
     * @time 2022/10/24 11:25:15
     */
    public static String decryptByPublicKey(String publicKey, String ciphertext) {
        RSA rsa = SecureUtil.rsa(null, publicKey);
        return rsa.decryptStr(ciphertext, KeyType.PublicKey);
    }
}
