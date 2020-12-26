package com.fantaike.template.api;


import com.fantaike.template.constant.ApiEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;


/**
 * @ClassName: JsonResponse
 * @Description: 接口统一返回数据格式
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:18
 * @Version: v1.0 文件初始创建
 */
@Data
@NoArgsConstructor
public class JsonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求响应码值，通常将码值在ApiEnum中维护
     */
    private String messageCode = null;

    /**
     * 请求响应说明，通常将码值在ApiEnum中维护
     */
    private String message = null;

    /**
     * 响应数据,例如用户列表查询时保存该list
     */
    private Object data = null;


    /**
     * @Description: 自定义响应返回数据，通常作用于返回不同状态，没有数据时
     * @param code
     * @param message
     * @Date: 2019/7/15 15:45
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse response(String code,String message) {
        return createJsonResponse(code, message, null);
    }

    /**
     * @Description: 自定义响应返回数据，通常作用于返回不同状态，没有数据时
     * @param apiEnum
     * @Date: 2019/7/8 20:19
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse response(ApiEnum apiEnum) {
        return createJsonResponse(apiEnum.getCode(), apiEnum.getName(), null);
    }

    /**
     * @Description: 自定义响应返回数据，通常作用于返回不同状态，有数据时
     * @param apiEnum
     * @param data 需要返回的数据
     * @Date: 2019/7/8 20:20
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse response(ApiEnum apiEnum, Object data) {
        return createJsonResponse(apiEnum.getCode(), apiEnum.getName(), data);
    }

    /**
     * @Description: 响应成功，定制化message
     * @param message
     * @Date: 2019/7/8 20:20
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse success(String message) {
        return createJsonResponse(ApiEnum.RSLT_CDE_000000.getCode(), message, null);
    }

    /**
     * @Description: 响应成功，定制化message，并添加返回数据
     * @param message
     * @param data
     * @Date: 2019/7/8 20:20
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse success(String message, Object data) {
        return createJsonResponse(ApiEnum.RSLT_CDE_000000.getCode(), message, data);
    }

    /**
     * @Description: 响应成功，添加返回数据
     * @param data
     * @Date: 2019/7/8 20:20
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse success(Object data) {
        return createJsonResponse(ApiEnum.RSLT_CDE_000000.getCode(), ApiEnum.RSLT_CDE_000000.getName(), data);
    }

    /**
     * @Description: 响应失败
     * @param 
     * @Date: 2019/7/8 20:21
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse fail() {
        return createJsonResponse(ApiEnum.RSLT_CDE_999999.getCode(), ApiEnum.RSLT_CDE_999999.getName(), null);
    }

    /**
     * @Description: 响应失败，添加失败原因
     * @param message
     * @Date: 2019/7/8 20:21
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    public static JsonResponse fail(String message) {
        return createJsonResponse(ApiEnum.RSLT_CDE_999999.getCode(), message, null);
    }

    /**
     * @Description: 创建对象
     * @param messageCode 状态码值
     * @param message 返回说明
     * @param data 返回数据
     * @Date: 2019/7/8 20:21
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    private static JsonResponse createJsonResponse(String messageCode, String message, Object data) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMessageCode(messageCode);
        jsonResponse.setMessage(message);
        //前端不好处理null 的情况 此处设置为空的对象
        if (data == null) {
            jsonResponse.setData(new HashMap<>(16));
        } else {
            jsonResponse.setData(data);
        }
        return jsonResponse;
    }
}


