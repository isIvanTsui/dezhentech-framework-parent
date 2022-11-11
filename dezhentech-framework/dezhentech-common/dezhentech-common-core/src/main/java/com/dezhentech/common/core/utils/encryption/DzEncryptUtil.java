package com.dezhentech.common.core.utils.encryption;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.dezhentech.common.core.utils.UtilsProperties;
import com.dezhentech.common.core.utils.encryption.asymmetric.DzEciesEncryptor;
import com.dezhentech.common.core.utils.encryption.asymmetric.DzKeyPairType;
import com.dezhentech.common.core.utils.encryption.asymmetric.DzRsaEncryptor;
import com.dezhentech.common.core.utils.encryption.asymmetric.DzSm2Encryptor;
import com.dezhentech.common.core.utils.objects.DzStringUtil;
import com.dezhentech.common.core.utils.yml.DzYmlUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.security.KeyPair;

/**
 * dz加密工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.encryption.DzEncryptUtil
 * @since 2022/11/10 16:42:20
 **/
@Slf4j
public class DzEncryptUtil extends SecureUtil {

    //private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

    /**
     * 工具包属性
     */
    private static UtilsProperties utilsProperties = DzYmlUtil.autoConfig(UtilsProperties.class);

    /**
     * 默认字符集
     */
    private static Charset defaultCharset = utilsProperties.getCharset();

    /**
     * 算法
     */
    private static DzEncryptAlgorithm algorithm = utilsProperties.getEncryption().getAlgorithm();

    /**
     * 默认密钥（对称）
     */
    private static String defaultKey = utilsProperties.getEncryption().getKey();


    /**
     * 默认Key长度(bit)
     */
    private static int defaultKeySize = utilsProperties.getEncryption().getKeySize();

    /**
     * 默认Key字节长度
     */
    private static int defaultKeyByteSize = defaultKeySize / 8;


    /**
     * 默认私钥（非对称）
     */
    private static String defaultPrivateKey = utilsProperties.getEncryption().getPrivateKey();

    /**
     * 默认公钥（非对称）
     */
    private static String defaultPublicKey = utilsProperties.getEncryption().getPublicKey();

//    private static byte[]          defaultKeyBytes    = defaultKey.getBytes(defaultCharset);

    /**
     * 生成密钥（仅用于对称加密）
     *
     * @param algorithm 算法
     * @param key       key
     * @return {@code byte[] }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 17:37:43
     */
    public static byte[] encodeKey(DzEncryptAlgorithm algorithm, String key) {

        // try {
        //
        // KeyGenerator keyGen = KeyUtil.getKeyGenerator(algorithm.name());
        // //Security.addProvider(GlobalBouncyCastleProvider.INSTANCE.getProvider());
        // SecureRandom secureRandom =
        // SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
        // secureRandom.setSeed(key.getBytes(defaultCharset));
        //
        // if (algorithm.name().startsWith("AES") || algorithm.name().startsWith("SM"))
        // {
        // keyGen.init(utilsProperties.getEncryptKeySize(), secureRandom);
        // } else {
        // keyGen.init(secureRandom);
        // }
        //
        // return keyGen.generateKey().getEncoded();
        //// Key keySpec = new SecretKeySpec(key.getBytes(), "AES");
        //// return keySpec.getEncoded();
        //
        // } catch (Exception e) {
        // throw new IllegalArgumentException(e);
        // }

        //如果是AES，只能按规定长度生成key （128/192/256 bits）byte长度 / 8
        String newKey = key;
        //        if (EncryptAlgorithm.AES == algorithm) {
        //            if (key.length() < defaultKeyByteSize) {
        //                //需要补齐
        //                log.warn("dezhentech.utils.defaultEncryptKey[{}]长度与encryptKeySize[{}/8={}]不符，将使用\'\\0\'补齐", key.length(), defaultKeySize, defaultKeyByteSize);
        //                newKey = StringUtil.fillAfter(key, '\0', defaultKeyByteSize);
        //            } else if (key.length() > defaultKeyByteSize) {
        //                //需要截断
        //                newKey = key.substring(0, defaultKeyByteSize);
        //                log.warn("dezhentech.utils.defaultEncryptKey[{}]长度与encryptKeySize[{}/8={}]不符，将按size截断", key.length(), defaultKeySize, defaultKeyByteSize);
        //            }
        //        }

        byte[] keyBytes = DzStringUtil.bytes(newKey,defaultCharset);

        return SecureUtil.generateKey(algorithm.name(), keyBytes).getEncoded();
    }

    /**
     * 生成密钥对（仅用于非对称加密）
     *
     * @param algorithm 算法
     * @return {@code KeyPair }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 17:37:36
     */
    public static KeyPair generateKeyPair(DzEncryptAlgorithm algorithm) {
        return SecureUtil.generateKeyPair(algorithm.name());
    }

    /**
     * 密钥位数补足
     * 按所缺的位置依次补足
     *
     * @param key 密钥
     * @return {@code byte[] }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 16:43:03
     */
    private static byte[] plus(String key) {
        // 密钥位数补足
        int plus = defaultKeyByteSize - key.length();
        byte[] data = key.getBytes(defaultCharset);
        byte[] raw = new byte[defaultKeyByteSize];
        byte[] plusBytes = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < defaultKeyByteSize; i++) {
            if (data.length > i) {
                raw[i] = data[i];
            } else {
                raw[i] = plusBytes[plus];
            }
        }

        return raw;
    }
    //	private static EncryptAlgorithm convertAlgorithm(SymmetricAlgorithm sa) {
    //		return EncryptAlgorithm.valueOf(sa.getValue());
    //	}

    /**
     * 加密为Base64字符串（对称加密）
     *
     * @param algorithm 算法
     * @param content   字符串
     * @param key       key
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 17:38:26
     */
    public static String encryptBase64(DzEncryptAlgorithm algorithm, String content, String key) {
        String en = null;

        switch (algorithm) {
            //对称加密
            case AES:
            case ARCFOUR:
            case Blowfish:
            case DES:
            case RC2:
            case PBEWithMD5AndDES:
            case PBEWithSHA1AndDESede:
            case PBEWithSHA1AndRC2_40:
            case SM4:
                // key
                byte[] encodedKey = encodeKey(algorithm, key);
                SymmetricCrypto sym = new SymmetricCrypto(algorithm.getValue(), encodedKey);
                en = sym.encryptBase64(content, defaultCharset);
                break;
            default:
                log.warn("无匹配算法");
                en = content;
                break;
        }
        return en;

    }

    /**
     * 加密为Base64字符串（非对称加密）
     *
     * @param algorithm  算法
     * @param content    字符串
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @param keyType    密钥类型
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 18:13:14
     */
    public static String encryptBase64(DzEncryptAlgorithm algorithm, String content, String privateKey, String publicKey, DzKeyPairType keyType) {
        String en = null;

        switch (algorithm) {
            //非对称加密
            case RSA:
            case RSA_ECB_PKCS1:
            case RSA_ECB:
            case RSA_None:
                DzRsaEncryptor rsa = createRsa(algorithm.getValue(), privateKey, publicKey);
                en = rsa.encryptBase64(content, keyType, defaultCharset);
                break;
            case ECIES:
                DzEciesEncryptor ecies = createEcies(algorithm.getValue(), privateKey, publicKey);
                en = ecies.encryptBase64(content, keyType, defaultCharset);
                break;
            case SM2:
                DzSm2Encryptor sm2 = createSm2(privateKey, publicKey);
                en = sm2.encryptBase64(content, keyType, defaultCharset);
                break;
            default:
                log.warn("无匹配算法");
                en = content;
                break;
        }
        return en;
    }

    /**
     * 加密为Hex字符串（对称加密）
     *
     * @param algorithm 算法
     * @param content   字符串
     * @param key       key
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 17:38:26
     */
    public static String encryptHex(DzEncryptAlgorithm algorithm, String content, String key) {
        String en = null;

        switch (algorithm) {
            //对称加密
            case AES:
            case ARCFOUR:
            case Blowfish:
            case DES:
            case RC2:
            case PBEWithMD5AndDES:
            case PBEWithSHA1AndDESede:
            case PBEWithSHA1AndRC2_40:
            case SM4:
                // key
                byte[] encodedKey = encodeKey(algorithm, key);
                SymmetricCrypto sym = new SymmetricCrypto(algorithm.getValue(), encodedKey);
                en = sym.encryptHex(content, defaultCharset);
                break;
            default:
                log.warn("无匹配算法");
                en = content;
                break;
        }
        return en;

    }

    /**
     * 加密为Hex字符串（非对称加密）
     *
     * @param algorithm  算法
     * @param content    字符串
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @param keyType    密钥类型
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 18:13:14
     */
    public static String encryptHex(DzEncryptAlgorithm algorithm, String content, String privateKey, String publicKey, DzKeyPairType keyType) {
        String en = null;

        switch (algorithm) {
            //非对称加密
            case RSA:
            case RSA_ECB_PKCS1:
            case RSA_ECB:
            case RSA_None:
                DzRsaEncryptor rsa = createRsa(algorithm.getValue(), privateKey, publicKey);
                en = rsa.encryptHex(content, keyType, defaultCharset);
                break;
            case ECIES:
                DzEciesEncryptor ecies = createEcies(algorithm.getValue(), privateKey, publicKey);
                en = ecies.encryptHex(content, keyType, defaultCharset);
                break;
            case SM2:
                DzSm2Encryptor sm2 = createSm2(privateKey, publicKey);
                en = sm2.encryptHex(content, keyType, defaultCharset);
                break;
            default:
                log.warn("无匹配算法");
                en = content;
                break;
        }
        return en;
    }


    /**
     * 加密为Base64字符串
     *
     * @param algorithm 算法
     * @param content   内容
     * @param key       密钥
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 19:14:23
     */
    public static String encryptBase64(String algorithm, String content, String key) {

        return encryptBase64(DzEncryptAlgorithm.valueOf(algorithm), content, key);
    }


    /**
     * 加密为Hex字符串
     *
     * @param algorithm 算法
     * @param content   内容
     * @param key       关键
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 19:14:48
     */
    public static String encryptHex(String algorithm, String content, String key) {
        return encryptHex(DzEncryptAlgorithm.valueOf(algorithm), content, key);
    }


    /**
     * 解密Base64或Hex字符串
     *
     * @param algorithm 算法
     * @param content   内容
     * @param key       密钥
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 19:15:01
     */
    public static String decrypt(DzEncryptAlgorithm algorithm, String content, String key) {
        String de = null;

        switch (algorithm) {
            //对称加密
            case AES:
            case ARCFOUR:
            case Blowfish:
            case DES:
            case RC2:
            case PBEWithMD5AndDES:
            case PBEWithSHA1AndDESede:
            case PBEWithSHA1AndRC2_40:
            case SM4:
                // key
                byte[] encodedKey = encodeKey(algorithm, key);
                SymmetricCrypto sym = new SymmetricCrypto(algorithm.getValue(), encodedKey);
                de = sym.decryptStr(content, defaultCharset);
                break;
            default:
                log.warn("无匹配算法");
                de = content;
                break;
        }
        return de;
    }

    /**
     * 解密Base64或Hex字符串
     *
     * @param algorithm 算法
     * @param content   内容
     * @param key       密钥
     * @return {@code String }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 19:15:24
     */
    public static String decrypt(String algorithm, String content, String key) {
        return decrypt(DzEncryptAlgorithm.valueOf(algorithm), content, key);
    }

    /**
     * 构造RSA
     * 私钥和公钥同时为空时生成一对新的私钥和公钥
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     * @param privateKey 私钥Hex或Base64表示，必须使用PKCS#8规范
     * @param publicKey  公钥Hex或Base64表示，必须使用X509规范
     * @return {@code DzRsaEncryptor }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 18:26:35
     */
    public static DzRsaEncryptor createRsa(String privateKey, String publicKey) {
        return new DzRsaEncryptor(privateKey, publicKey);
    }

    /**
     * 构造RSA
     * 私钥和公钥同时为空时生成一对新的私钥和公钥
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     *
     * @param rsaAlgorithm rsa算法
     * @param privateKey   私钥Hex或Base64表示，必须使用PKCS#8规范
     * @param publicKey    公钥Hex或Base64表示，必须使用X509规范
     * @return {@code DzRsaEncryptor }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 18:26:21
     */
    public static DzRsaEncryptor createRsa(String rsaAlgorithm, String privateKey, String publicKey) {
        return new DzRsaEncryptor(rsaAlgorithm, privateKey, publicKey);
    }

    /**
     * 创建sm2
     * 私钥和公钥同时为空时生成一对新的私钥和公钥
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     *
     * @param privateKey 私钥Hex或Base64表示，必须使用PKCS#8规范
     * @param publicKey  公钥Hex或Base64表示，必须使用X509规范
     * @return {@code DzSm2Encryptor }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 18:26:07
     */
    public static DzSm2Encryptor createSm2(String privateKey, String publicKey) {
        return new DzSm2Encryptor(privateKey, publicKey);
    }

    /**
     * 创建种类
     *
     * @param privateKey 私钥Hex或Base64表示，必须使用PKCS#8规范
     * @param publicKey  公钥Hex或Base64表示，必须使用X509规范
     * @return {@code DzEciesEncryptor }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 18:26:01
     */
    public static DzEciesEncryptor createEcies(String privateKey, String publicKey) {
        return new DzEciesEncryptor(privateKey, publicKey);
    }

    /**
     * @param privateKey 私钥Hex或Base64表示，必须使用PKCS#8规范
     * @param publicKey  公钥Hex或Base64表示，必须使用X509规范
     * @return {@code RSAEncryptor }
     * @descriptions 构造ECIES
     * 私钥和公钥同时为空时生成一对新的私钥和公钥
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:13:59
     */
    public static DzEciesEncryptor createEcies(String eciesAlgorithm, String privateKey, String publicKey) {
        return new DzEciesEncryptor(eciesAlgorithm, privateKey, publicKey);
    }

}
