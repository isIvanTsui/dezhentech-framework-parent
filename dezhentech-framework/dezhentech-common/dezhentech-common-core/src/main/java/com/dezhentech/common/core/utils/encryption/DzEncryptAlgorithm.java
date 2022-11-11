package com.dezhentech.common.core.utils.encryption;

/**
 * 加密算法
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.encryption.DzEncryptAlgorithm
 * @since 2022/11/10 20:36:44
 **/
public enum DzEncryptAlgorithm {
	NONE("NONE"),

	/**
	 * 对称加密部分
	 */
	/** 默认的AES加密方式：AES/ECB/PKCS5Padding */
	AES("AES"),
	ARCFOUR("ARCFOUR"),
	Blowfish("Blowfish"),
	/** 默认的DES加密方式：DES/ECB/PKCS5Padding */
	DES("DES"),
	/** 3DES算法，默认实现为：DESede/ECB/PKCS5Padding */
	DESede("DESede"),
	RC2("RC2"),

	PBEWithMD5AndDES("PBEWithMD5AndDES"),
	PBEWithSHA1AndDESede("PBEWithSHA1AndDESede"),
	PBEWithSHA1AndRC2_40("PBEWithSHA1AndRC2_40"),

	/** 国密SM4（对称） */
	SM4("SM4"),

	/**
	 * 非对称部分
	 */

	/** RSA算法 */
	RSA("RSA"),
	/** RSA算法，此算法用了默认补位方式为RSA/ECB/PKCS1Padding */
	RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding"),
	/** RSA算法，此算法用了默认补位方式为RSA/ECB/NoPadding */
	RSA_ECB("RSA/ECB/NoPadding"),
	/** RSA算法，此算法用了RSA/None/NoPadding */
	RSA_None("RSA/None/NoPadding"),

	/** 集成加密方案（elliptic curve integrate encrypt scheme） */
	ECIES("ECIES"),

	/** 国密SM2（非对称） */
	SM2("SM2");

	private final String value;

	/**
	 * 构造
	 * @param value 算法字符表示，区分大小写
	 */
	DzEncryptAlgorithm(String value) {
		this.value = value;
	}

	/**
	 * 获取算法字符串表示，区分大小写
	 * @return 算法字符串表示
	 */
	public String getValue() {
		return this.value;
	}
};
