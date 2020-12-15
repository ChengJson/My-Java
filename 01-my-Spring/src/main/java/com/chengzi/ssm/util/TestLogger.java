package com.chengzi.ssm.util;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName: TestLogger
 * @Description: 测试日志打印
 * @Author: chengli
 * @Date: 2020/12/14 11:17
 * @Version: v1.0 文件初始创建
 */

public class TestLogger {

    private static Logger logger = LoggerFactory.getLogger(TestLogger.class);

    @Test
    public void test() {
        logger.trace("trace日志");
        logger.debug("debug日志");
        logger.info("消息日志");
        logger.warn("告警日志");
        logger.error("报错了", new RuntimeException());
    }

    @Test
    public void test1(){
        try {
            BeanUtils.copyProperties(new TestLogger(),new TestLogger());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
