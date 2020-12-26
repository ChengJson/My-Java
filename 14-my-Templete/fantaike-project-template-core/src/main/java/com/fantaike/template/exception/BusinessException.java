package com.fantaike.template.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BusinessException
 * @Description: 自定义业务异常
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:49
 * @Version: v1.0 文件初始创建
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMessages = new HashMap();

    private String errorCode = null;

    public BusinessException() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public Map<String, String> getErrorMessages() {
        return this.errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessage(String fieldName, String message) {
        this.errorMessages.put(fieldName, message);
    }


}
