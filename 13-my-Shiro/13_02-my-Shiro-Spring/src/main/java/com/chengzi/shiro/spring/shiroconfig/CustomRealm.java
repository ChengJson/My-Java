package com.chengzi.shiro.spring.shiroconfig;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 9:41
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> userHashMap = new HashMap<>();

    {
        userHashMap.put("zhangsan", "22da30b7be8c8e23ec0d4d95ed562fbc");
        super.setName("customRealm");
    }

    //做授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> rolesSet = getRolesByUserName(username);
        Set<String> premissions = getPremissionByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(premissions);
        simpleAuthorizationInfo.setRoles(rolesSet);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPremissionByUserName(String username) {
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    private Set<String> getRolesByUserName(String username) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    //做认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //从登录主体的信息中获取用户名
        String username = (String) token.getPrincipal();

        //根据用户名从数据库中获取凭证
        String password = getPasswordByUsername(username);
        SimpleAuthenticationInfo authenticationInfo = null;
        if (password != null) {
            authenticationInfo = new SimpleAuthenticationInfo(username, password, "customRealm");
        } else {
            return null;
        }
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("thisIsSalt"));
        return authenticationInfo;
    }

    private String getPasswordByUsername(String username) {
        String s = userHashMap.get(username);
        return s;
    }


    @Test
    public void md5Hash(){
        Md5Hash thisIsSalt = new Md5Hash("123", "thisIsSalt");
        System.out.println(thisIsSalt);

    }
}
