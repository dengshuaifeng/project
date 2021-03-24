package cn.itcast.constant;

/**
 * @Author: Weny
 * @Date:2021/3/22
 */
public enum ExceptionEnum {
    /**
     * 异常
     */
    TOKEN_OUT_OF_DATE(1201, "token已过期"),
    TOKEN_FORMAT_ERROR(1202, "token格式错误"),
    TOKEN_CREATE_ERROR(1203, "token构造错误"),
    TOKEN_SIGN_DEFAULT(1204, "签名失败"),
    ILLEGAL_ERROR(1205, "非法参数异常"),
    TOKEN_EMPTY(1206,"token为空"),
    USER_NO_EXIST(1207,"用户不存在"),
    WRONG_PWD(1208,"账号或者密码错误"),
    PARAM_IS_EMPTY(1209,"参数不能为空"),
    USER_NOT_VALID(1210,"用户未认证");


    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 错误描述
     */
    private String errorMsg;

    ExceptionEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
