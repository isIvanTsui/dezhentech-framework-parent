package com.dezhentech.framework.common.encrypt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @description: Rsa秘钥对
 * @title: com.dezhentech.common.encrypt.RsaEntity
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/24 11:11:42
 * @version: 1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsaKeyPair implements Serializable {

    private static final long serialVersionUID = -8370479104542556641L;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;
}
