package com.dezhentech.common.core.utils.signer;

import cn.hutool.crypto.asymmetric.SM2;

/**
 * 签名实现类（SM2）<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.signer.DzSignerSM2
 * @since 2022/11/10 20:42:08
 **/
public class DzSignerSm2 extends DzAbstractSigner implements DzSigner {
	private SM2 sm2;

	public DzSignerSm2() {
		super();
		sm2 = new SM2(privateKey, publicKey);
	}
	public DzSignerSm2(String privateKey, String publicKey) {
		super(DzSignerAlgorithm.SM2, privateKey, publicKey);
		sm2 = new SM2(privateKey, publicKey);

	}

	@Override
	public byte[] sign(byte[] data) {
		return sm2.sign(data);
	}

	@Override
	public boolean verify(byte[] data, byte[] sign) {
		return sm2.verify(data, sign);
	}

}
