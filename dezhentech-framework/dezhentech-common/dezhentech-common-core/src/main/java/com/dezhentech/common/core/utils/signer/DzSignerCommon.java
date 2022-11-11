package com.dezhentech.common.core.utils.signer;

import cn.hutool.crypto.asymmetric.Sign;

/**
 * 签名实现类（共同）<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.signature.SignerCommon
 * @since 2022/11/10 19:38:05
 **/
public class DzSignerCommon extends DzAbstractSigner {
	private Sign signer;

	/**
	 * 构造<br>
	 *  使用配置的私钥和公钥验证和签名
	 */
	public DzSignerCommon() {
		super();
		signer = new Sign(algorithm.name(), privateKey, publicKey);
	}

	/**
	 * 构造<br>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做签名或验证
	 * 
	 * @param algorithm {@link DzSignerAlgorithm}
	 * @param privateKeyStr 私钥Hex或Base64表示
	 * @param publicKeyStr 公钥Hex或Base64表示
	 */
	public DzSignerCommon(DzSignerAlgorithm algorithm, String privateKeyStr, String publicKeyStr) {
		super(algorithm, privateKeyStr, publicKeyStr);
		signer = new Sign(algorithm.name(), privateKeyStr, publicKeyStr);
	}

	@Override
	public byte[] sign(byte[] data) {

		return signer.sign(data);
	}

	@Override
	public boolean verify(byte[] data, byte[] sign) {
		return signer.verify(data, sign);
	}

}
