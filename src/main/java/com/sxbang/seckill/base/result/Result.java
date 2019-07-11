package com.sxbang.seckill.base.result;

import lombok.Data;

/**
 * @author kaneki
 * @date 2019/6/24 16:18
 */
@Data
public class Result<T> {
    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> failure() {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.FAIL);
        return result;
    }

    public static <T> Result<T> failure(T data) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.FAIL);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failure(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setResultCode(resultCode);
        return result;
    }

    public static <T> Result<T> failure(ResultCode resultCode, T data) {
        Result<T> result = new Result<>();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
