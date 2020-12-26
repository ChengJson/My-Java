package com.fantaike.template.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: CommonLogger
 * @Description: 统一日志输出类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:12
 * @Version: v1.0 文件初始创建
 */
public class CommonLogger {

    private static Logger log = LoggerFactory.getLogger(CommonLogger.class);


    /**
     * @Description: 装JSON数据调用info打出json格式内容
     * @param businessCode    业务码
     * @param businessName    业务名称
     * @param businessContent 业务内容
     * @param params          自定义参数（格式key=value (String) ,eg: "key=value"）
     * @Date: 2019/7/8 20:12
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    public static void info(String businessCode, String businessName, String businessContent,
                            String... params) {
        JSONObject json = new JSONObject();
        try {
            commonLog(json, businessCode, businessName, businessContent, params);
            log.info(json.toString());
        } catch (Exception e) {
            json.clear();
            json.put(BUSINESS_CODE, businessCode);
            json.put(BUSINESS_NAME, businessName);
            if (e != null) {
                //错误异常信息
                json.put(EXCEPTION, e);
            }
            log.info(json.toString());
        }
    }
    
    /**
     * @Description: 错误信息日志,组装JSON数据调用error打出json格式内容
     * @param businessCode    业务码
     * @param businessName    业务名称
     * @param businessContent 业务内容
     * @param e               异常对象e
     * @param params          自定义参数（格式key=value (String) ,eg: "key=value"）
     * @Date: 2019/7/8 20:11
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    public static void error(String businessCode, String businessName, String businessContent, Throwable e, String... params) {
        JSONObject json = new JSONObject();
        try {
            commonLog(json, businessCode, businessName, businessContent, params);
            if (e != null) {
                //错误异常信息
                json.put(EXCEPTION, e);
            }
            log.error(json.toString());
        } catch (Exception e1) {
            json.clear();
            json.put(BUSINESS_CODE, businessCode);
            json.put(BUSINESS_NAME, businessName);
            if (e != null) {
                json.put(EXCEPTION, e);
            }
            if (e1 != null) {
                json.put(LOGGER_EXCEPTION, e1);
            }
            log.error(json.toString());
        }
    }

    /**
     * @param json
     * @param businessCode
     * @param businessName
     * @param businessContent
     * @param params          void
     * @Description(功能描述) :  重构日志组装默认参数
     * @author(作者) ：  曹轩
     * @date (开发日期)          :  2017-12-15 上午9:12:49
     */
    private static void commonLog(JSONObject json, String businessCode, String businessName, String businessContent, String... params) {
        //类、方法信息
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String className = stacks[3].getClassName();
        String methodName = stacks[3].getMethodName();
        int lineNumber = stacks[3].getLineNumber();
        json.put(CLASS_NAME, className);
        json.put(METHOD_NAME, methodName);
        json.put(LINE_NUMBER, lineNumber);
        //业务信息
        json.put(BUSINESS_CODE, businessCode);
        json.put(BUSINESS_NAME, businessName);
        json.put(BUSINESS_CONTENT, businessContent);
        //自定义参数
        if (params != null && params.length > 0) {
            String[] paramInfo;
            for (String param : params) {
                if (param.indexOf(SYMBOL) > -1) {
                    paramInfo = param.split(SYMBOL);
                    json.put(paramInfo[0].trim(), paramInfo[1].trim());
                }
            }
        }
    }

    /**
     * 业务码值
     */
    private static final String BUSINESS_CODE = "businessCode";

    /**
     * 业务名称
     */
    private static final String BUSINESS_NAME = "businessName";

    /**
     * 业务内容
     */
    private static final String BUSINESS_CONTENT = "businessContent";

    /**
     * 异常信息
     */
    private static final String EXCEPTION = "exception";

    /**
     * 日志异常
     */
    private static final String LOGGER_EXCEPTION = "loggerException";

    /**
     * 类名
     */
    private static final String CLASS_NAME = "className";

    /**
     * 方法名
     */
    private static final String METHOD_NAME = "methodName";

    /**
     * 行号
     */
    private static final String LINE_NUMBER = "lineNumber";

    /**
     * 自定义参数分隔符
     */
    private static final String SYMBOL = "=";

    /**
     * 防止初始化
     */
    private CommonLogger() {

    }
}