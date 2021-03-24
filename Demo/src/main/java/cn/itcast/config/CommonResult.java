package cn.itcast.config;

import lombok.Data;
/**
 *@Author: Weny
 *@Des:返回封装
 */
@Data
public class CommonResult<T> {
    private T data;
    private int code;
    private String message;

    public CommonResult(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }
    public CommonResult(){

    }
}
