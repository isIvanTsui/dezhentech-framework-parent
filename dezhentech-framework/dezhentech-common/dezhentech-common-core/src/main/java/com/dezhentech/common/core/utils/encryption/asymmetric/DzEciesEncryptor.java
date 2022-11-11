package com.dezhentech.common.core.utils.encryption.asymmetric;

import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;

import java.nio.charset.Charset;

/**
 * dzECIES加密器<br />
 * ECIES：全称集成加密方案（elliptic curve integrate encrypt scheme）<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.encryption.asymmetric.DzEciesEncryptor
 * @since 2022/11/10 20:42:45
 **/
public class DzEciesEncryptor extends ECIES {
	private static final long serialVersionUID = 7223795621180827613L;

	/**
	 * 构造，生成新的私钥公钥对
	 */
	public DzEciesEncryptor() {
		super();
	}
	/**
	 * 构造<br>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
	 *
	 * @param privateKeyStr 私钥Hex或Base64表示，必须使用PKCS#8规范
	 * @param publicKeyStr  公钥Hex或Base64表示，必须使用X509规范
	 */
	public DzEciesEncryptor(String privateKeyStr, String publicKeyStr) {
		super(privateKeyStr, publicKeyStr);
	}


	/**
	 * 构造 <br>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
	 *
	 * @param privateKey 私钥，可以使用PKCS#8、D值或PKCS#1规范
	 * @param publicKey  公钥，可以使用X509、Q值或PKCS#1规范
	 * @return {@code  }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:21:44
	 */
	public DzEciesEncryptor(byte[] privateKey, byte[] publicKey) {
		super(privateKey, publicKey);
	}

	/**
	 * 构造 <br>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
	 *
	 * @param eciesAlgorithm ecies算法
	 * @param privateKey  私钥
	 * @param publicKey   公钥
	 * @return {@code  }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:21:41
	 */
	public DzEciesEncryptor(String eciesAlgorithm, String privateKey, String publicKey) {
		super(eciesAlgorithm, privateKey, publicKey);
	}

	/**
	 * 加密
	 *
	 * @param data    数据
	 * @param type 密钥类型
	 * @return 加密后的密文
	 * @since 4.1.0
	 */
	public byte[] encrypt(byte[] data, DzKeyPairType type) {
		return encrypt(data, KeyType.valueOf(type.name()));
	}
	
	/**
	 * 分组加密
	 *
	 * @param data    数据
	 * @param type 密钥类型
	 * @return 加密后的密文
	 * @since 4.1.0
	 */
	public String encryptBcd(String data, DzKeyPairType type, Charset charset) {
		return encryptBcd(data, KeyType.valueOf(type.name()), charset);
	}
	
	/**
	 * 编码为Base64字符串
	 *
	 * @param data    数据
	 * @param type 密钥类型
	 * @return 加密后的密文
	 * @since 4.1.0
	 */
	public String encryptBase64(String data, DzKeyPairType type, Charset charset) {
		return encryptBase64(data, charset, KeyType.valueOf(type.name()));
	}
	
	/**
	 * 编码为Base64字符串
	 *
	 * @param data    数据
	 * @param type 密钥类型
	 * @return 加密后的密文
	 * @since 4.1.0
	 */
	public String encryptHex(String data, DzKeyPairType type, Charset charset) {
		return encryptHex(data, charset, KeyType.valueOf(type.name()));
	}
	
	/**
	 * 解密
	 *
	 * @param data    SM2密文，实际包含三部分：ECC公钥、真正的密文、公钥和原文的SM3-HASH值
	 * @param type 私钥或公钥 {@link KeyType}
	 * @return 加密后的bytes
	 */
	public byte[] decrypt(byte[] data, DzKeyPairType type) {
		return decrypt(data, KeyType.valueOf(type.name()));
	}
	
	/**
	 * 分组解密
	 *
	 * @param data    数据
	 * @param type 密钥类型
	 * @return 解密后的密文
	 * @since 4.1.0
	 */
	public String decryptStrFromBcd(String data, DzKeyPairType type, Charset charset) {
		return decryptStrFromBcd(data, KeyType.valueOf(type.name()), charset);
	}
	
	/**
	 * 从Hex或Base64字符串解密，编码为UTF-8格式
	 *
	 * @param data    Hex（16进制）或Base64字符串
	 * @param type 私钥或公钥 {@link KeyType}
	 * @return 解密后的bytes
	 * @since 4.5.2
	 */
	public String decryptStr(String data, DzKeyPairType type, Charset charset) {
		return decryptStr(data, KeyType.valueOf(type.name()), charset);
	}

	
	/**
	 * 加密
	 *
	 * @param data    数据
	 * @return 加密后的密文
	 * @since 4.1.0
	 */
	public byte[] encrypt(byte[] data) {
		return encrypt(data, KeyType.PublicKey);
	}

	/**
	 * 解密
	 *
	 * @param data    SM2密文，实际包含三部分：ECC公钥、真正的密文、公钥和原文的SM3-HASH值
	 * @return 加密后的bytes
	 */
	public byte[] decrypt(byte[] data) {
		return decrypt(data, KeyType.PrivateKey);
	}

}
