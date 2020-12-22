package com.chengzi.shiro.simple.jdbcRealm;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/21 18:04
 */
public class JdbcRealmTest {
    DruidDataSource dataSource = new DruidDataSource();

    {
        //dataSource.setUrl("jdbc:mysql://localhost:3306");
        dataSource.setUsername("jdbc:oracle:thin:@172.31.1.5:1521:dspdb");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void authenicatinTest(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //权限开关
        jdbcRealm.setPermissionsLookupEnabled(true);

        jdbcRealm.setAuthenticationQuery("自定义认证的语句");
        jdbcRealm.setUserRolesQuery("自定义角色查询的语句");
        jdbcRealm.setPermissionsQuery("自定义授权的语句");
        //构建环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken();
        subject.login(token);
        System.out.println("result"+subject.isAuthenticated());
    }


}
