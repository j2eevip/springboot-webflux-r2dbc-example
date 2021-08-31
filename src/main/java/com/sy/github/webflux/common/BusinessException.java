package com.sy.github.webflux.common;

/**
 * @author Sherlock
 * @since 2021/8/31-21:40
 */
public class BusinessException extends RuntimeException {
    private final Integer code;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
