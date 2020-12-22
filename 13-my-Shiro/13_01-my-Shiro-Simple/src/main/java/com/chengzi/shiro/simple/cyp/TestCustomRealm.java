package com.chengzi.shiro.simple.cyp;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 11:00
 */
public class TestCustomRealm {
    @Test
    public void testCustomRealm(){
        //构建环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        CustomRealm customRealm = new CustomRealm();
        defaultSecurityManager.setRealm(customRealm);


        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);


        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123");

        subject.login(token);

    }
}
