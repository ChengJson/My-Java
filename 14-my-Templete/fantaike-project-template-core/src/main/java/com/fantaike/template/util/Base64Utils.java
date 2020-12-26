package com.fantaike.template.util;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.CharsetUtil;

/**
 * @ClassName Base64Utils
 * @Description base64 工具类
 * @Author wugz
 * @Date 2019/7/10 19:08
 * @Version 1.0
 */
public class Base64Utils {

    /**
     * @Description: base64编码
     * @param source 被编码的base64字符串
     * @Date: 2019/7/10 19:10
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String encode(CharSequence source) {
        return Base64Encoder.encode(source);
    }

    /**
     * @Description: base64编码
     * @param source 被编码的base64字符串
     * @param charset 字符集
     * @Date: 2019/7/10 19:12
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String encode(CharSequence source, String charset) {
        return Base64Encoder.encode(source, CharsetUtil.charset(charset));
    }

    /**
     * @Description: base64解码
     * @param source 被解码的base64字符串
     * @Date: 2019/7/10 19:13
     * @Author: wuguizhen
     * @Return java.lang.String 被加密后的字符串
     * @Throws
     */
    public static String decodeStr(CharSequence source) {
        return Base64Decoder.decodeStr(source);
    }

    /**
     * @Description: base64解码
     * @param source 被解码的base64字符串
     * @param charset 字符集
     * @Date: 2019/7/10 19:13
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String decodeStr(CharSequence source, String charset) {
        return Base64Decoder.decodeStr(source, CharsetUtil.charset(charset));
    }

}
