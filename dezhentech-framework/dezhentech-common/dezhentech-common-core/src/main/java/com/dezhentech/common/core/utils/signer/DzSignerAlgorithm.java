package com.dezhentech.common.core.utils.signer;

/**
 * 签名加密算法<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.signer.DzSignerAlgorithm
 * @since 2022/11/10 20:41:19
 **/
public enum DzSignerAlgorithm {
	// The RSA signature algorithm
	NONEwithRSA,

	// The MD2/MD5 with RSA Encryption signature algorithm
	MD2withRSA,
	MD5withRSA,

	// The signature algorithm with SHA-* and the RSA
	SHA1withRSA,
	SHA256withRSA,
	SHA384withRSA,
	SHA512withRSA,

	// The Digital Signature Algorithm
	NONEwithDSA,
	// The DSA with SHA-1 signature algorithm
	SHA1withDSA,

	// The ECDSA signature algorithms
	NONEwithECDSA,
	SHA1withECDSA,
	SHA256withECDSA,
	SHA384withECDSA,
	SHA512withECDSA,

	SM2,

	/** 彩虹破解MD5可能需要3分钟左右，而BCrypt就需要14年之久。所以一般都推荐使用bcrypt。
	 * BCrypt加密原理：
	 * 输入的明文密码通过10次循环加盐后得到myHash（版本+salt），然后存入数据库。
	 * 系统在验证用户的口令时，需要从myHash中取出salt跟password进行hash；
	 * 得到的结果保存在DB中的hash进行比对，如果一致才算验证通过。
	 */
	BCRYPT;

}
