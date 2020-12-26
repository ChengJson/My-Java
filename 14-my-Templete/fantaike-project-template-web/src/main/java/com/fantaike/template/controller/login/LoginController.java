package com.fantaike.template.controller.login;

import org.apache.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.common.shiro.security.JwtProperties;
import com.fantaike.template.common.shiro.security.JwtUtil;
import com.fantaike.template.common.shiro.security.SecurityConsts;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.domain.setting.SysUsr;
import com.fantaike.template.dto.nosql.RedisService;
import com.fantaike.template.service.setting.SysUsrService;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginController
 * @Description 登录控制器
 * @Author wugz
 * @Date 2019/6/16 10:35
 * @Version 1.0
 */
@RestController
public class LoginController {

    @Reference
    private SysUsrService sysUsrService;

    @Reference
    private RedisService redisService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @return com.fantaike.template.api.JsonResponse
     * @Author wugz
     * @Description 登录验证
     * @Date 2019/6/16 10:40
     * @Param [usrCde, password]
     */
    @PostMapping("/login")
    public JsonResponse login(String usrCde, String password, HttpServletResponse response) {
        //根据用户编号查询用户信息
        SysUsr userInfo = sysUsrService.getByUsrId(usrCde);
//        //无此帐号，抛出“未知帐号”异常
        if (ObjectIsNullUtil.isNullOrEmpty(userInfo)) {
            CommonLogger.info(usrCde, BusinessNameConstant.LOGIN, "用户未注册");
            return JsonResponse.response(ApiEnum.RSLT_CDE_001001);
        }
        String status = userInfo.getStatus();
        if (!CommonEnum.STAT_ACTIVE.getCode().equals(status)) {
            //未激活或者已经删除的帐号，抛出“帐号禁用”
            CommonLogger.info(usrCde, BusinessNameConstant.LOGIN, "用户未激活");
            return JsonResponse.response(ApiEnum.RSLT_CDE_001003);
        }
        SimpleHash hash = new SimpleHash("md5", password, usrCde, 2);
        String inputMD5Password = hash.toHex();
        if(!inputMD5Password.equals(userInfo.getUsrPassword())){
            CommonLogger.info(usrCde, BusinessNameConstant.LOGIN, "密码错误");
            return JsonResponse.response(ApiEnum.RSLT_CDE_001002);
        }
        //登录成功，生成token放到 响应头中
        loginSuccess(usrCde,response);
        return JsonResponse.success("登录成功");
    }

    /**
     * @return com.fantaike.template.api.JsonResponse
     * @Author wugz
     * @Description 退出系统
     * @Date 2019/6/16 10:40
     * @Param []
     */
    @GetMapping("/exit")
    public JsonResponse exit() {
        ShiroUserAccountUtil.logout();
        return JsonResponse.success("退出成功");
    }

    private void loginSuccess(String account, HttpServletResponse response) {

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        //生成token
        JSONObject json = new JSONObject();
        String token = JwtUtil.sign(account, currentTimeMillis);
        json.put("token", token);

        //更新RefreshToken缓存的时间戳
        String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        redisService.set(refreshTokenKey, currentTimeMillis, jwtProperties.getTokenExpireTime()*60);

        //写入header
        response.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
    }

    @RequestMapping("/test")
    public Object testController(){
        CommonLogger.info("123","123","121321","123");
        return "ok";
    }
}
