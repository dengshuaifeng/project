package cn.itcast.util;

import cn.itcast.constant.ExceptionEnum;
import lombok.Data;

/**
 * @Author: Weny
 * @Date:2021/3/22
 * @Desc:自定义异常
 */
@Data
public class ExceptionUtil extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    protected int errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public ExceptionUtil() {
        super();
    }

    public ExceptionUtil(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ExceptionUtil(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ExceptionUtil(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getErrorMsg());
        this.errorCode=exceptionEnum.getErrorCode();
        this.errorMsg=exceptionEnum.getErrorMsg();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
