package com.dezhentech.common.core.utils.encryption.asymmetric;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.nio.charset.Charset;

/**
 * dz sm2加密机
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.encryption.asymmetric.DzSm2Encryptor
 * @since 2022/11/10 20:42:29
 **/
public class DzSm2Encryptor extends SM2 {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1307449810506762051L;

    /**
     * @descriptions 构造，生成新的私钥公钥对
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:14:28
     */
    public DzSm2Encryptor() {
        super();
    }

    /**
     * @param privateKey 私钥Hex或Base64表示，必须使用PKCS#8规范
     * @param publicKey  公钥Hex或Base64表示，必须使用X509规范
     * @descriptions 构造<br>
     * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:22
     */
    public DzSm2Encryptor(String privateKey, String publicKey) {
        super(privateKey, publicKey);
    }

    /**
     * @param privateKey 私钥，可以使用PKCS#8、D值或PKCS#1规范
     * @param publicKey  公钥，可以使用X509、Q值或PKCS#1规范
     * @descriptions 构造 <br>
     * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:26
     */
    public DzSm2Encryptor(byte[] privateKey, byte[] publicKey) {
        super(privateKey, publicKey);
    }

    /**
     * @param data 数据
     * @param type 密钥类型
     * @return {@code byte[] }
     * @descriptions 加密
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:30
     */
    public byte[] encrypt(byte[] data, DzKeyPairType type) {
        return encrypt(data, KeyType.valueOf(type.name()));
    }

    /**
     * @param data 数据
     * @param type 密钥类型
     * @return {@code String }
     * @descriptions 分组加密
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 11:12:34
     */
    public String encryptBcd(String data, DzKeyPairType type, Charset charset) {
        return encryptBcd(data, KeyType.valueOf(type.name()), charset);
    }

    /**
     * @param data 数据
     * @param type 密钥类型
     * @return {@code String }
     * @descriptions 编码为Base64字符串
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:37
     */
    public String encryptBase64(String data, DzKeyPairType type, Charset charset) {
        return encryptBase64(data, charset, KeyType.valueOf(type.name()));
    }

    /**
     * @param data 数据
     * @param type 密钥类型，私钥或公钥
     * @return {@code String }
     * @descriptions 编码为Base64字符串
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:39
     */
    public String encryptHex(String data, DzKeyPairType type, Charset charset) {
        return encryptHex(data, charset, KeyType.valueOf(type.name()));
    }

    /**
     * @param data SM2密文，实际包含三部分：ECC公钥、真正的密文、公钥和原文的SM3-HASH值
     * @param type 密钥类型，私钥或公钥
     * @return {@code byte[] }
     * @descriptions 解密
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:42
     */
    public byte[] decrypt(byte[] data, DzKeyPairType type) {
        return decrypt(data, KeyType.valueOf(type.name()));
    }

    /**
     * @param data 数据
     * @param type 密钥类型，私钥或公钥
     * @return {@code String }
     * @descriptions 分组解密
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:45
     */
    public String decryptStrFromBcd(String data, DzKeyPairType type, Charset charset) {
        return decryptStrFromBcd(data, KeyType.valueOf(type.name()), charset);
    }

    /**
     * @param data Hex（16进制）或Base64字符串
     * @param type 密钥类型，私钥或公钥
     * @return {@code String }
     * @descriptions 从Hex或Base64字符串解密，编码为UTF-8格式
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 11:12:47
     */
    public String decryptStr(String data, DzKeyPairType type, Charset charset) {
        return decryptStr(data, KeyType.valueOf(type.name()), charset);
    }


}
