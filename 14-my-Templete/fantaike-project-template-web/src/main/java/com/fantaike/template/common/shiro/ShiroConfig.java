package com.fantaike.template.common.shiro;


import com.alibaba.dubbo.config.annotation.Reference;
import com.fantaike.template.common.shiro.security.JwtFilter;
import com.fantaike.template.common.shiro.security.JwtProperties;
import com.fantaike.template.dto.nosql.RedisService;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Description: shiro配置类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:28
 * @Version: v1.0 文件初始创建
 */
@Configuration
public class ShiroConfig {

    @Reference
    RedisService redisService;

   /**
    * @Description: 此方法设置对应的过滤条件和跳转条件
    * @param 
    * @Date: 2019/7/8 20:29
    * @Author: wuguizhen
    * @Return org.apache.shiro.spring.web.ShiroFilterFactoryBean
    * @Throws
    */
    @Bean
    public ShiroFilterFactoryBean shirFilter(JwtProperties jwtProperties) {
        //记得打日志
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt", new JwtFilter(jwtProperties,redisService));
        shiroFilterFactoryBean.setFilters(filterMap);

        //拦截器
        Map<String,String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/exit", "anon");
        filterRuleMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(myShiroRealm());
        
        /*--- Code modify start: 使用token鉴权，关闭shiro自带的sesision wuguizhen 2019/7/11 16:08 ---*/
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        /*---- Code modify end:  wuguizhen 2019/7/11 16:08 ----*/

        securityManager.setSubjectFactory(subjectFactory());

        // 配置 缓存管理类 cacheManager
        //securityManager.setCacheManager(cacheManager());
        return securityManager;
    }


    @Bean
    public StatelessDefaultSubjectFactory subjectFactory(){
        return new StatelessDefaultSubjectFactory();
    }

    @Bean
    public ShiroDbRealm myShiroRealm() {
        ShiroDbRealm myShiroRealm = new ShiroDbRealm();
        return myShiroRealm;
    }

    /**
     * 注册全局异常处理
     *
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new ShiroExceptionHandler();
    }

    /**
     * 为解决shiro注解不生效的问题
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
