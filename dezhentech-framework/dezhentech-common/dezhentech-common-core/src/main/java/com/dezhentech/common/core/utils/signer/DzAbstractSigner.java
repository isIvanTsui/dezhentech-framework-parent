package com.dezhentech.common.core.utils.signer;

import com.dezhentech.common.core.utils.UtilsProperties;
import com.dezhentech.common.core.utils.codec.DzBase64Util;
import com.dezhentech.common.core.utils.codec.DzHexUtil;
import com.dezhentech.common.core.utils.objects.DzStringUtil;
import com.dezhentech.common.core.utils.yml.DzYmlUtil;

import java.nio.charset.Charset;

/**
 * 签名抽象类（加密签名、摘要签名）<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.signer.DzAbstractSigner
 * @since 2022/11/10 20:44:14
 **/
public abstract class DzAbstractSigner implements DzSigner {
	
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
	

	/**
	 * 构造 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做签名或验证
	 *
	 */
	public DzAbstractSigner() {
		this(algorithm,privateKey, publicKey);
	}
	
	/**
	 * 构造 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做签名或验证
	 * 
	 * @param algorithm {@link DzSignerAlgorithm}
	 * @param privateKeyStr 私钥Hex或Base64表示
	 * @param publicKeyStr 公钥Hex或Base64表示
	 */
	public DzAbstractSigner(DzSignerAlgorithm algorithm, String privateKeyStr, String publicKeyStr) {
		this(algorithm.name(),privateKeyStr, publicKeyStr);
	}
	
	/**
	 * 构造 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做签名或验证
	 * 
	 * @param algorithm {@link DzSignerAlgorithm}
	 * @param privateKeyStr 私钥Hex或Base64表示
	 * @param publicKeyStr 公钥Hex或Base64表示
	 */
	public DzAbstractSigner(String algorithm, String privateKeyStr, String publicKeyStr) {
		DzAbstractSigner.algorithm= DzSignerAlgorithm.valueOf(algorithm);
		DzAbstractSigner.privateKey=privateKeyStr;
		DzAbstractSigner.publicKey=publicKeyStr;
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return 16进制签名
	 */
	@Override
	public abstract byte[] sign(byte[] data);

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign 16进制签名
	 * @return 是否验证通过
	 */
	@Override
	public abstract boolean verify(byte[] data, byte[] sign) ;
	

	@Override
	public String sign(String data) {
		return signHex(data);
	}

	@Override
	public boolean verify(String data, String sign) {
		return verifyHex(data,sign);
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return 16进制签名
	 */
	@Override
	public String signHex(String data) {
		byte[] dataBytes = DzStringUtil.bytes(data, defaultCharset);
		return DzHexUtil.encodeHexStr(sign(dataBytes));
	}

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign 16进制签名
	 * @return 是否验证通过
	 */
	@Override
	public boolean verifyHex(String data, String sign) {
		byte[] dataBytes = DzStringUtil.bytes(data, defaultCharset);
		byte[] signBytes = DzHexUtil.decodeHex(sign);
		return verify(dataBytes, signBytes);
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return Base64签名
	 */
	@Override
	public String signBase64(String data) {
		byte[] dataBytes = DzStringUtil.bytes(data, defaultCharset);
		return DzBase64Util.encode(sign(dataBytes));
	}

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign Base64签名
	 * @return 是否验证通过
	 */
	@Override
	public boolean verifyBase64(String data, String sign) {
		byte[] dataBytes = DzStringUtil.bytes(data, defaultCharset);
		byte[] signBytes = DzBase64Util.decode(sign);
		return verify(dataBytes, signBytes);
	}


}
