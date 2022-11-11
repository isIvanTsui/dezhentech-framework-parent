package com.dezhentech.common.core.utils.digester;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.HMac;
import com.dezhentech.common.core.utils.UtilsProperties;
import com.dezhentech.common.core.utils.yml.DzYmlUtil;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 摘要工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.digester.DigesterUtil
 * @since 2022/11/10 16:22:28
 **/
public class DzDigesterUtil extends DigestUtil {

	/**
	 * 工具属性
	 */
	private static UtilsProperties utilsProperties = DzYmlUtil.autoConfig(UtilsProperties.class);
	/**
	 * 默认字符集
	 */
	private static Charset defaultCharset = utilsProperties.getCharset();

	/**
	 * 算法（默认SM3）
	 */
	private static DzDigesterAlgorithm algorithm = utilsProperties.getDisgest().getAlgorithm();

	/**
	 * 生成摘要
	 *
	 * @param content 字符串
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 19:49:10
	 */
	public static String digest(String content){

		return digest(algorithm, content);
	}

	/**
	 * 生成摘要
	 *
	 * @param algorithm 算法
	 * @param content   字符串
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 20:01:07
	 */
	public static String digest(DzDigesterAlgorithm algorithm, String content){

		Digester digester = DigestUtil.digester(algorithm.name());

		String digestHex = digester.digestHex(content, defaultCharset);

		return digestHex;
	}

	/**
	 * 生成摘要
	 *
	 * @param file 文件
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 19:49:10
	 */
	public static String digest(File file){

		return digest(algorithm, file);
	}

	/**
	 * 生成摘要
	 *
	 * @param algorithm 算法
	 * @param file      文件
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 20:01:46
	 */
	public static String digest(DzDigesterAlgorithm algorithm, File file){

		Digester digester = DigestUtil.digester(algorithm.name());

		String digestHex = digester.digestHex(file);

		return digestHex;
	}


	/**
	 * 编码bcrypt
	 *
	 * @param content 字符串
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 16:22:13
	 */
	public static String digestBcrypt(String content) {
		String encoded = DigestUtil.bcrypt(content);
		return encoded;
	}

	/**
	 * 验证bcrypt
	 *
	 * @param content 字符串
	 * @param signed  已签名的hash值（加密后的值）
	 * @return boolean
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 16:22:10
	 */
	public static boolean verifyBcrypt(String content, String signed) {
		return bcryptCheck(content, signed);
	}

	/**
	 * 编码hmac
	 *
	 * @param content 字符串
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 16:22:07
	 */
	public static String digestHmacMd5Hex(String content, String key, Charset charset) {
		byte[] keyBytes = key.getBytes(charset);

		HMac hmac = SecureUtil.hmacMd5(keyBytes);
		String encoded = hmac.digestHex(content, charset);
		return encoded;
	}

	/**
	 * 编码hmacSha1
	 *
	 * @param content 字符串
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 16:22:07
	 */
	public static String digestHmacSha1Hex(String content, String key, Charset charset) {
		byte[] keyBytes = key.getBytes(charset);

		HMac hmac = SecureUtil.hmacSha1(keyBytes);
		String encoded = hmac.digestHex(content, charset);
		return encoded;
	}

	/**
	 * 编码hmacSha256
	 *
	 * @param content 字符串
	 * @return {@code String }
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 16:22:07
	 */
	public static String digestHmacSha256Hex(String content, String key, Charset charset) {
		byte[] keyBytes = key.getBytes(charset);

		HMac hmac = SecureUtil.hmacSha256(keyBytes);
		String encoded = hmac.digestHex(content, charset);
		return encoded;
	}

}
