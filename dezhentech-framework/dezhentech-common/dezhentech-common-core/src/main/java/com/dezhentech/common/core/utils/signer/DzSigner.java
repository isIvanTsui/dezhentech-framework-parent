package com.dezhentech.common.core.utils.signer;

/**
 * 签名接口类<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.signature.Signer
 * @since 2022/11/10 19:35:34
 **/
public interface DzSigner {

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return 16进制签名
	 */
	public byte[] sign(byte[] data);

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign 16进制签名
	 * @return 是否验证通过
	 */
	public boolean verify(byte[] data, byte[] sign);
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return 默认16进制签名
	 */
	public String sign(String data);

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign 默认16进制签名
	 * @return 是否验证通过
	 */
	public boolean verify(String data, String sign);
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return 16进制签名
	 */
	public String signHex(String data);

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign 16进制签名
	 * @return 是否验证通过
	 */
	public boolean verifyHex(String data, String sign);
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 签名数据
	 * @return Base64签名
	 */
	public String signBase64(String data);

	/**
	 * 用公钥检验数字签名的合法性
	 * 
	 * @param data 签名数据
	 * @param sign Base64签名
	 * @return 是否验证通过
	 */
	public boolean verifyBase64(String data, String sign);
}
