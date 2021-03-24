package cn.itcast.controller;


import cn.itcast.config.CommonResult;
import cn.itcast.constant.ExceptionEnum;
import cn.itcast.constant.ResultEnum;

public class BaseController {

    public static class Result {

        public static <T> CommonResult success(T data) {
            return new CommonResult(data, ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage());
        }
        public static <T>CommonResult success() {
            return new CommonResult(null, ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage());
        }

        public static <T>CommonResult error(String message){
            return new CommonResult( null,ResultEnum.ERROR.getCode(),message);
        }
        public static <T>CommonResult error(){
            return new CommonResult( null,ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage());
        }
        public static <T>CommonResult error(ExceptionEnum exceptionEnum){
            return new CommonResult(null,exceptionEnum.getErrorCode(),exceptionEnum.getErrorMsg());
        }
    }
}
