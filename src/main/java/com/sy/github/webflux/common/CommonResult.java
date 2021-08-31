package com.sy.github.webflux.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

/**
 * @author Sherlock
 * @since 2021/8/31-21:13
 */
public class CommonResult<T> implements Serializable {
    public static Integer SUCCESS_CODE = 200;
    public static <T> CommonResult<T> error(Integer code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = SUCCESS_CODE;
        result.data = data;
        result.msg = "success";
        return result;
    }
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }

    @Override
    public String toString() {
        return "CommonResult{" +
            "code=" + code +
            ", message='" + msg + '\'' +
            ", data=" + data +
            '}';
    }
}
