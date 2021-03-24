package cn.itcast.constant;
/**
 *@Author: Weny
 *@Des:
 */
public enum ResultEnum {
    /**
     * 返回枚举
     */
    SUCCESS(200,"success"),
    ERROR(500,"error"),
    EXCEPTION(202,"异常"),
    SELF(201,"self");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultEnum(int code, String message){
        this.code=code;
        this.message=message;
    }

}
