
package com.dezhentech.common.core.utils.codec;

import cn.hutool.core.codec.Base64;
import com.dezhentech.common.core.utils.UtilsProperties;
import com.dezhentech.common.core.utils.yml.DzYmlUtil;

import java.nio.charset.Charset;

/**
 * dzbase64工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.codec.DzBase64Util
 * @since 2022/11/10 20:36:10
 **/
public class DzBase64Util extends Base64{
	private static UtilsProperties utilsProperties = DzYmlUtil.autoConfig(UtilsProperties.class);

	private static Charset defaultCharset = utilsProperties.getCharset();
	
//
//	/**
//	 * 将字符串编码为Base64字符串<br/>
//	 *
//	 * @param content
//	 * @return String
//	 * @author zzz
//	 * @version 1.0.0.0, 2019年12月10日 下午5:59:42
//	 *
//	 * @修改人 ：zzz
//	 * @修改时间：2019年12月10日 下午5:59:42 @修改备注：
//	 *
//	 */
//	public static String encode(String content) {
//		return Base64.encode(content, defaultCharset);
//	}
//
//	/**
//	 * 将字节码编码为Base64字符串<br/>
//	 *
//	 * @param content
//	 * @return String
//	 * @author zzz
//	 * @version 1.0.0.0, 2019年12月10日 下午6:00:28
//	 *
//	 * @修改人 ：zzz
//	 * @修改时间：2019年12月10日 下午6:00:28 @修改备注：
//	 *
//	 */
//	public static String encode(byte[] content) {
//		return Base64.encode(content);
//	}

//	/**
//	 * 将Base64字节码解码为字符串<br/>
//	 *
//	 * @param content
//	 * @return String
//	 * @author zzz
//	 * @version 1.0.0.0, 2019年12月10日 下午6:01:12
//	 *
//	 * @修改人 ：zzz
//	 * @修改时间：2019年12月10日 下午6:01:12 @修改备注：
//	 *
//	 */
//	public static String decode(byte[] content) {
//		return new String(Base64.decode(content), defaultCharset);
//	}

}
