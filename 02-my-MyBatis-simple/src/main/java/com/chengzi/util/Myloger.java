package com.chengzi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Myloger
 * @Description: 日志工具类
 * @Author: chengli
 * @Date: 2020/12/14 14:38
 * @Version: v1.0 文件初始创建
 */
public class Myloger {
    public static <T> Logger getLogger(Class<T> tClass) {
        return LoggerFactory.getLogger(Myloger.class);
    }

}
