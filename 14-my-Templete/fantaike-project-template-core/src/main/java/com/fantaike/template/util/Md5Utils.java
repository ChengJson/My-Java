package com.fantaike.template.util;

import cn.hutool.crypto.digest.MD5;

import java.io.File;
import java.io.InputStream;

/**
 * @ClassName Md5Utils
 * @Description md5工具类
 * @Author wugz
 * @Date 2019/7/10 19:15
 * @Version 1.0
 */
public class Md5Utils {
    
    /**
     * @Description: 生成16位MD5摘要
     * @param salt 盐值
     * @param data 数据
     * @Date: 2019/7/10 19:17
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String digestHex16(byte[] salt,String data) {
        MD5 md5 = new MD5(salt);
        return md5.digestHex16(data);
    }

    /**
     * @Description: 生成16位MD5摘要
     * @param salt 盐值
     * @param data 数据
     * @Date: 2019/7/10 19:17
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String digestHex16(byte[] salt,InputStream data) {
        MD5 md5 = new MD5(salt);
        return md5.digestHex16(data);
    }

    /**
     * @Description: 生成16位MD5摘要
     * @param salt 盐值
     * @param data 数据
     * @Date: 2019/7/10 19:17
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String digestHex16(byte[] salt,File data) {
        MD5 md5 = new MD5(salt);
        return md5.digestHex16(data);
    }
}
