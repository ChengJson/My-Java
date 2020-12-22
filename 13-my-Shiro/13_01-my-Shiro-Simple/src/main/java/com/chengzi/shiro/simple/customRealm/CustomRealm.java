package com.chengzi.shiro.simple.customRealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

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
        userHashMap.put("zhangsan", "123");
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
            authenticationInfo = new SimpleAuthenticationInfo(username, password, "CustomRealm");
        } else {
            return null;
        }

        return authenticationInfo;
    }

    private String getPasswordByUsername(String username) {
        String s = userHashMap.get(username);
        return s;
    }
}
