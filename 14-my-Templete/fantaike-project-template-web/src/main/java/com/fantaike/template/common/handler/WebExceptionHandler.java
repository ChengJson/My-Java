package com.fantaike.template.common.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.exception.BusinessException;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author wugz
 * @Description 统一异常处理类
 * @Date  2019/4/19 13:16
 * @Param 
 * @return 
 */
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * @Author wugz
     * @Description 自定义异常
     * @Date  2019/4/19 13:10
     * @Param [e]
     * @return com.caxins.factory.common.api.JsonResponse
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public JsonResponse handleBDException(BusinessException e) {
        CommonLogger.error(ShiroUserAccountUtil.getUserId(),BusinessNameConstant.EXCEPTION_HANDLE,e.getMessage(),e);
        String message = e.getMessage() == null ? "系统异常" : e.getMessage();
        return JsonResponse.fail(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse handleAuthorizationException(Exception e) {
        CommonLogger.error(ShiroUserAccountUtil.getUserId(),BusinessNameConstant.EXCEPTION_HANDLE,e.getMessage(),e);
        return JsonResponse.fail();
    }
    
    /**
     * @Author wugz
     * @Description 登录失效异常捕获
     * @Date  2019/6/18 16:25
     * @Param [ex]
     * @return com.fantaike.template.api.JsonResponse
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public JsonResponse resolveUnauthenticatedException(UnauthenticatedException ex, HttpServletResponse response){
        return JsonResponse.response(ApiEnum.RSLT_CDE_001004);
    }

    /**
     * @Author wugz
     * @Description token异常
     * @Date  2019/6/18 16:25
     * @Param [ex]
     * @return com.fantaike.template.api.JsonResponse
     */
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseBody
    public JsonResponse resolveTokenExpiredException(TokenExpiredException ex, HttpServletResponse response){
        return JsonResponse.response(ApiEnum.RSLT_CDE_001006.getCode(),ex.getMessage());
    }

    /**
     * @Author wugz
     * @Description 捕获没有权限异常
     * @Date  2019/6/26 12:56
     * @Param [e]
     * @return com.fantaike.template.api.JsonResponse
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public JsonResponse resolveUnauthorizedException(UnauthorizedException e){
        CommonLogger.error(ShiroUserAccountUtil.getUserId(),BusinessNameConstant.EXCEPTION_HANDLE,e.getMessage(),e);
        return JsonResponse.response(ApiEnum.RSLT_CDE_001005);
    }
    
    /**
     * @Author wugz
     * @Description 处理数据校验异常
     * @Date  2019/6/18 19:18
     * @Param [bindException]
     * @return com.fantaike.template.api.JsonResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public JsonResponse validExceptionHandler(BindException bindException) {
        String errorMessages = String.join(",",bindException.getAllErrors().
                stream().
                map(ObjectError :: getDefaultMessage).
                collect(Collectors.toList()));
        return JsonResponse.fail(errorMessages);
    }

    /**
     * @Author wugz
     * @Description 捕获dto里验证注解抛出的异常
     * @Date  2019/6/28 14:15
     * @Param [ex]
     * @return com.fantaike.template.api.JsonResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public JsonResponse resolveConstraintViolationException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if(!ObjectIsNullUtil.isNullOrEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation constraintViolation :constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
            return JsonResponse.fail(errorMessage);
        }
        return JsonResponse.fail(ex.getMessage());
    }

    /**
     * @Author wugz
     * @Description 捕获dto里验证注解抛出的异常
     * @Date  2019/6/28 14:15
     * @Param [ex]
     * @return com.fantaike.template.api.JsonResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonResponse resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if(!ObjectIsNullUtil.isNullOrEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return JsonResponse.fail(errorMessage);
        }
        return JsonResponse.fail(ex.getMessage());
    }

}
