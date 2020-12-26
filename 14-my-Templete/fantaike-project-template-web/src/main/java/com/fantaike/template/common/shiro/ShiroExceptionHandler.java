package com.fantaike.template.common.shiro;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.util.CommonLogger;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author wugz
 * @Description shiro 异常处理类
 * @Date 2019/6/16 11:14
 */
public class ShiroExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        CommonLogger.error("", BusinessNameConstant.LOGIN, "捕获shiro异常", ex);
        JsonResponse jsonResponse;
        if (ex instanceof UnauthenticatedException) {
            //处理登录失效异常
            jsonResponse = JsonResponse.response(ApiEnum.RSLT_CDE_001004);
        } else if (ex instanceof UnauthorizedException) {
            //处理用户无此权限异常
            jsonResponse = JsonResponse.response(ApiEnum.RSLT_CDE_001005);
        } else {
            //处理未知异常
            jsonResponse = JsonResponse.fail();
        }
        Map attributesMap = (Map<String, String>) JSON.parse(JSONObject.toJSONString(jsonResponse));
        view.setAttributesMap(attributesMap);
        mv.setView(view);
        return mv;
    }
}
