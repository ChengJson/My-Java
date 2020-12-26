package com.fantaike.template.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DBException
 * @Description: 数据库操作异常
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:50
 * @Version: v1.0 文件初始创建
 */
public class DBException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMessages = new HashMap();

    private String errorCode = null;

    public DBException() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String message, Throwable cause) {
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
