package com.fantaike.template.common.shiro.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: JwtProperties
 * @Description: token验证配置类
 * @Author: wuguizhen
 * @Date: 2019/7/15 16:59
 * @Version: v1.0 文件初始创建
 */
@Data
@ConfigurationProperties(prefix = "token")
@Component
public class JwtProperties {

    /**
     * token过期时间，单位分钟
     */
    Long tokenExpireTime;

    /**
     * 更新令牌时间，单位分钟
     */
    Long refreshCheckTime;

    /**
     * token加密密钥
     */
    String secretKey;
}
