package com.sy.github.webflux.common;

import lombok.Data;

/**
 * @author Sherlock
 * @since 2021/8/31-21:41
 */
public enum ResultEnum {
    SUCCESS(0, "成功。"),
    PARAM_ERROR(1001,"参数验证失败。"),
    UNKNOWN_ERROR(9999,"未知错误。"),
    ;
    private final Integer code;
    private final String msg;
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
