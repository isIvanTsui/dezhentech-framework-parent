package com.dezhentech.common.core.utils.signer;

import com.dezhentech.common.core.utils.UtilsProperties;
import com.dezhentech.common.core.utils.digester.DzDigesterAlgorithm;
import com.dezhentech.common.core.utils.digester.DzDigesterUtil;
import com.dezhentech.common.core.utils.yml.DzYmlUtil;

import java.nio.charset.Charset;

/**
 * 签名工具类<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.signature.SignUtil
 * @since 2022/11/10 19:35:58
 **/
public class DzSignerUtil {

	/**
	 * 工具属性
	 */
	private static UtilsProperties utilsProperties = DzYmlUtil.autoConfig(UtilsProperties.class);
	/**
	 * 默认字符
	 */
	protected static Charset defaultCharset = utilsProperties.getCharset();
	/**
	 * 算法
	 */
	protected static DzSignerAlgorithm algorithm = utilsProperties.getSign().getAlgorithm();
	/**
	 * 公钥
	 */
	protected static String privateKey = utilsProperties.getSign().getPrivateKey();
	/**
	 * 私钥
	 */
	protected static String publicKey = utilsProperties.getSign().getPublicKey();


	public static String sign(DzDigesterAlgorithm algorithm,String content) {
		return DzDigesterUtil.digest(algorithm, content);
	}

	public static boolean verify(DzDigesterAlgorithm algorithm,String content, String signed) {
		return DzDigesterUtil.digest(algorithm, content).equals(signed);
	}
	
	public static String signBcrypt(String content) {
		return DzDigesterUtil.digestBcrypt(content);
	}

	public static boolean verifyBcrypt(String content, String signed) {
		return DzDigesterUtil.verifyBcrypt(content, signed);
	}
	
	public static DzSigner createSigner() {
		return createSigner(algorithm);
	}
	
	public static DzSigner createSigner(DzSignerAlgorithm alg) {
		return createSigner(alg, privateKey, publicKey);
	}
	
	public static DzSigner createSigner(DzSignerAlgorithm alg, String privateKey, String publicKey) {
		DzSigner signer = null;
		if (DzSignerAlgorithm.SM2.equals(alg)) {
			signer = new DzSignerSm2(privateKey, publicKey);
		}else {
			signer = new DzSignerCommon(alg, privateKey, publicKey);
		}
		return signer;
	}
	
	/**
	 * 
	 * 签名<br/>
	 * 默认签名算法和私钥、公钥
	 * @param content
	 * @return String
	 *
	 */
	public static String sign(String content) {
		DzSigner signer = createSigner();
		return signer.sign(content);
	}
	
	/**
	 * 
	 * 签名<br/>
	 *
	 * @param signer
	 * @param content
	 * @return String
	 *
	 */
	public static String sign(DzSigner signer, String content) {
		return signer.sign(content);
	}
	
	/**
	 * 验证签名<br/>
	 *
	 * @param content
	 * @param signed
	 * @return boolean
	 *
	 */
	public static boolean verify(String content, String signed) {
		DzSigner signer = createSigner();
		
		return signer.verify(content, signed);
	}

	/**
	 * 验证签名<br/>
	 *
	 * @param signer
	 * @param content
	 * @param signed
	 * @return boolean
	 *
	 */
	public static boolean verify(DzSigner signer, String content, String signed) {
		// 验证签名
		return signer.verify(content, signed);
	}
	
}
