package com.chengzi.shiro.spring.handler;

import com.chengzi.shiro.spring.exception.MyCustomException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/16 16:58
 */
@ControllerAdvice
public class GlobalExceptionReolver {

    @ExceptionHandler(value = MyCustomException.class)
    public ModelAndView myCustomException(MyCustomException ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","custom Exception occur..");
        modelAndView.setViewName("myException");
        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView MyCustomException(Exception ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","custom Exception occur.."+ex.getMessage());
        modelAndView.setViewName("myException");
        return modelAndView;
    }

    @ExceptionHandler(value = ShiroException.class)
    public ModelAndView ShiroException(ShiroException ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","Shiro Exception occur.."+ex.getMessage());
        modelAndView.setViewName("myException");
        return modelAndView;
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ModelAndView ShiroException(AuthenticationException ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","Shiro Exception occur.."+ex.getMessage());
        modelAndView.setViewName("myException");
        return modelAndView;
    }


}
