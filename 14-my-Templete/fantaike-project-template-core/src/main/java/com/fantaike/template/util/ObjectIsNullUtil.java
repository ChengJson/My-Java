package com.fantaike.template.util;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: ObjectIsNullUtil
 * @Description: 判断对象是否为空的工具类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:13
 * @Version: v1.0 文件初始创建
 */
public class ObjectIsNullUtil {

    /**
     * @Description: 判断该对象是否为空，为空返回false，否则返回 true
     * @param obj 需要判断的对象
     * @Date: 2019/7/8 20:14
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    public static boolean notNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    /**
     * @Description: 判断对象是否为空 为空返回true，否则返回 false
     * @param obj 需要判断的对象
     * @Date: 2019/7/8 20:14
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

}
