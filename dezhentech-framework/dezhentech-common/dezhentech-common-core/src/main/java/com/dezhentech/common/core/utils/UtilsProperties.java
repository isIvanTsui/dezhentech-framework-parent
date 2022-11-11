package com.dezhentech.common.core.utils;


import com.dezhentech.common.core.utils.digester.DzDigesterAlgorithm;
import com.dezhentech.common.core.utils.encryption.DzEncryptAlgorithm;
import com.dezhentech.common.core.utils.signer.DzSignerAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 工具属性
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.UtilsProperties
 * @since 2022/11/10 16:10:12
 **/
@Data
@RefreshScope
@Lazy(false)
@ConfigurationProperties(prefix = "dezhentech.utils")
public class UtilsProperties implements Serializable {
	/**
	 * 串行版本uid
	 */
	private static final long serialVersionUID = -905067762535205954L;
	/**
	 * 默认字符编码
	 */
	private Charset charset = StandardCharsets.UTF_8;
	/**
	 * 加密
	 */
	private Encryption encryption;
	/**
	 * 签名
	 */
	private Sign sign;

	/**
	 * 摘要
	 */
	private Disgest disgest;
	/**
	 * 校验
	 */
	private Validation validation;

	/**
	 * 加密
	 *
	 * @author xu.zhang@dezhentech.com
	 * @version 1.0.0
	 * @title com.dezhentech.common.core.utils.UtilsProperties.Encryption
	 * @since 2022/11/10 16:10:52
	 **/
	@Data
	public class Encryption{
		/** 默认加密算法 */
		private DzEncryptAlgorithm algorithm = DzEncryptAlgorithm.SM2;
		/** 默认加密钥匙大小 */
		private int keySize = 128;
		/** 默认加密钥匙 */
		private String key = "dezhentech.com?1";
		/** 默认加密偏移 */
		private String iv = "1234567812345678";
		
		/** 默认私钥 */
		private String privateKey = "dezhentech.com?1";
		
		/** 默认公钥 */
		private String publicKey = "dezhentech.com?1";
	}

	/**
	 * 签名
	 *
	 * @author xu.zhang@dezhentech.com
	 * @version 1.0.0
	 * @title com.dezhentech.common.core.utils.UtilsProperties.Sign
	 * @since 2022/11/10 16:10:48
	 **/
	@Data
	public class Sign {
		/** 默认签名算法（国密SM2） */
		private DzSignerAlgorithm algorithm = DzSignerAlgorithm.SM2;
		/** 默认私钥 */
		private String privateKey = "dezhentech.com?1";
		
		/** 默认公钥 */
		private String publicKey = "dezhentech.com?1";
	}

	/**
	 * 摘要
	 *
	 * @author xu.zhang@dezhentech.com
	 * @version 1.0.0
	 * @title com.dezhentech.common.core.utils.UtilsProperties.Disgest
	 * @since 2022/11/10 16:10:45
	 **/
	@Data
	public class Disgest {
		/**
		 * 默认摘要算法（国密SM3）
		 */
		private DzDigesterAlgorithm algorithm = DzDigesterAlgorithm.SM3;

	    /**
	     * 盐值
	     */
		private String salt = "dezhentech.com?1";
	    /**
	     * 加盐位置，即将盐值字符串放置在数据的index数，默认0
	     */
		private int saltPosition= 0;
	    /**
	     * 摘要次数，当此值小于等于1,默认为1。
	     */
		private int digestCount = 1;
	}

	/**
	 * 验证
	 *
	 * @author xu.zhang@dezhentech.com
	 * @version 1.0.0
	 * @title com.dezhentech.common.core.utils.UtilsProperties.Validation
	 * @since 2022/11/10 16:10:42
	 **/
	@Data
	public class Validation {
		private Password password;
	}

	/**
	 * 密码
	 *
	 * @author xu.zhang@dezhentech.com
	 * @version 1.0.0
	 * @title com.dezhentech.common.core.utils.UtilsProperties.Password
	 * @since 2022/11/10 16:10:39
	 **/
	@Data
	public class Password {
		private boolean containsNumber = true;

		private boolean containsSpecial = true;

		private boolean containsEnglish = true;
		private boolean containsUppercase = true;
		private boolean containsLowercase = true;

		private boolean containsChinese = true;

		private int lengthMin = 8;
		private int lengthMax = 18;

		private int typeNum =3;
	}

}
