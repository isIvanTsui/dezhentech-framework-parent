package com.dezhentech.common.core.utils.encryption.asymmetric;


import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.dezhentech.common.core.utils.objects.DzStringUtil;

import java.nio.charset.Charset;

/**
 * RSA加密器
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.encryption.asymmetric.DzRsaEncryptor
 * @since 2022/11/10 20:39:05
 **/
public class DzRsaEncryptor extends RSA {

	private static final long serialVersionUID = 4265565898539084202L;

	/**
	 * 构造，生成新的私钥公钥对
	 */
	public DzRsaEncryptor() {
		super();
	}

	/**
	 * 构造，生成新的私钥公钥对
	 *
	 * @param rsaAlgorithm 自定义RSA算法，例如RSA/ECB/PKCS1Padding
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:23
	 */
	public DzRsaEncryptor(String rsaAlgorithm) {
		super(rsaAlgorithm);
	}

	/**
	 * 构造<br>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
	 *
	 * @param privateKeyStr 私钥Hex或Base64表示
	 * @param publicKeyStr  公钥Hex或Base64表示
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:20
	 */
	public DzRsaEncryptor(String privateKeyStr, String publicKeyStr) {
		super(privateKeyStr, publicKeyStr);
	}

	/**
	 * 构造<br>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
	 *
	 * @param rsaAlgorithm  自定义RSA算法，例如RSA/ECB/PKCS1Padding
	 * @param privateKeyStr 私钥Hex或Base64表示
	 * @param publicKeyStr  公钥Hex或Base64表示
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:16
	 */
	public DzRsaEncryptor(String rsaAlgorithm, String privateKeyStr, String publicKeyStr) {
		super(rsaAlgorithm, privateKeyStr, publicKeyStr);
	}


	/**
	 * 加密
	 *
	 * @param data 数据
	 * @param type 密钥类型，私钥或公钥
	 * @return {@code byte[] }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:13
	 */
	public byte[] encrypt(byte[] data, DzKeyPairType type) {
		return encrypt(data, KeyType.valueOf(type.name()));
	}

	/**
	 * 分组加密
	 *
	 * @param data    数据
	 * @param type    密钥类型，私钥或公钥
	 * @param charset 字符集
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:11
	 */
	public String encryptBcd(String data, DzKeyPairType type, Charset charset) {
		return encryptBcd(data, KeyType.valueOf(type.name()), charset);
	}

	/**
	 * 编码为Hex字符串
	 *
	 * @param content 被加密的字符串
	 * @param type    密钥类型，私钥或公钥
	 * @param charset 字符集
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:09
	 */
	public String encryptHex(String content, DzKeyPairType type, Charset charset) {
		return encryptHex(DzStringUtil.bytes(content, charset), KeyType.valueOf(type.name()));
	}

	/**
	 * 编码为Base64字符串
	 *
	 * @param content 被加密的字符串
	 * @param type    密钥类型，私钥或公钥
	 * @param charset 字符集
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:06
	 */
	public String encryptBase64(String content, DzKeyPairType type, Charset charset) {
		return encryptBase64(DzStringUtil.bytes(content, charset), KeyType.valueOf(type.name()));
	}

	/**
	 * 解密
	 *
	 * @param data 被解密的数据
	 * @param type 密钥类型，私钥或公钥
	 * @return {@code byte[] }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:54:02
	 */
	public byte[] decrypt(byte[] data, DzKeyPairType type) {
		return decrypt(data, KeyType.valueOf(type.name()));
	}

	/**
	 * 解密为字符串，密文需为Hex（16进制）或Base64字符串<br/>
	 *
	 * @param content 被解密的字符串
	 * @param type    密钥类型，私钥或公钥
	 * @param charset 字符集
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:53:59
	 */
	public String decryptStr(String content, DzKeyPairType type, Charset charset) {
		return decryptStr(content, KeyType.valueOf(type.name()), charset);
	}


	/**
	 * 解密为字符串，密文需为BCD格式<br/>
	 *
	 * @param content 被解密的字符串
	 * @param type    密钥类型，私钥或公钥
	 * @param charset 字符集
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 18:53:56
	 */
	public String decryptStrFromBcd(String content, DzKeyPairType type, Charset charset) {
		return decryptStrFromBcd(content, KeyType.valueOf(type.name()), charset);
	}
}
