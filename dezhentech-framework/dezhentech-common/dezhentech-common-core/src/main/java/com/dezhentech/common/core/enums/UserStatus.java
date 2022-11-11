package com.dezhentech.common.core.enums;

/**
 * @description: 用户状态
 * @title: com.dezhentech.common.core.enums.UserStatus
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/11/08 10:39:18
 * @version: 1.0.0
 **/
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
