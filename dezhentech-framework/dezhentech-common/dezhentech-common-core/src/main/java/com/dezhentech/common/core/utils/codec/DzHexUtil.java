package com.dezhentech.common.core.utils.codec;

import cn.hutool.core.util.HexUtil;
import com.dezhentech.common.core.utils.UtilsProperties;
import com.dezhentech.common.core.utils.yml.DzYmlUtil;

import java.nio.charset.Charset;

/**
 * dz十六进制工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.codec.DzHexUtil
 * @since 2022/11/10 20:43:18
 **/
public class DzHexUtil extends HexUtil {
	private static UtilsProperties utilsProperties = DzYmlUtil.autoConfig(UtilsProperties.class);

	private static Charset defaultCharset = utilsProperties.getCharset();

	// /**
	// * 字节流转成十六进制表示
	// */
	// public static String encode(byte[] src) {
	// if (src == null || src.length == 0) {
	// return null;
	// }
	// String strHex = "";
	// StringBuilder sb = new StringBuilder("");
	// for (int n = 0; n < src.length; n++) {
	// strHex = Integer.toHexString(src[n] & 0xFF);
	// sb.append((strHex.length() == 1) ? "0" + strHex : strHex); //
	// 每个字节由两个字符表示，位数不够，高位补0
	// }
	// return sb.toString().trim();
	// }
	//
	// /**
	// * 字符串转成字节流
	// */
	// public static byte[] decode(String src) {
	// if (src == null || src.isEmpty()) {
	// return null;
	// }
	//
	// int m = 0, n = 0;
	// int byteLen = src.length() / 2; // 每两个字符描述一个字节
	// byte[] ret = new byte[byteLen];
	// for (int i = 0; i < byteLen; i++) {
	// m = i * 2 + 1;
	// n = m + 1;
	// int intVal = Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m,
	// n));
	// ret[i] = Byte.valueOf((byte)intVal);
	// }
	// return ret;
	// }

	/**
	 * @param src 字符串
	 * @return {@code String }
	 * @descriptions 字节流转成十六进制表示，默认编码UTF-8
	 * @author xu.zhang@dezhentech.com
	 * @time 2022/11/10 10:19:56
	 */
	public static String encodeHexStr(String src) {
		return encodeHexStr(src, defaultCharset);
	}


	/**
	 * @param src 十六进制String
	 * @return {@code String }
	 * @descriptions 将十六进制字符数组转换为字符串，默认编码UTF-8
	 * @author xu.zhang@dezhentech.com
	 * @time 2022/11/10 10:20:03
	 */
	public static String decodeHexStr(String src) {
		return decodeHexStr(src, defaultCharset);
	}
}
