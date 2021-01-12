package com.chengzi.shiro.spring.shiroconfig.realm;

import com.chengzi.shiro.spring.shiroconfig.consts.SecurityConsts;
import com.chengzi.shiro.spring.shiroconfig.jwt.JwtToken;
import com.chengzi.shiro.spring.util.JedisUtil;
import com.chengzi.shiro.spring.util.JwtUtil;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 9:41
 */
public class CustomTokenRealm extends AuthorizingRealm {

    @Autowired
    private JedisUtil cacheClient;


    Map<String, String> userHashMap = new HashMap<>();

    {
        userHashMap.put("zhangsan", "22da30b7be8c8e23ec0d4d95ed562fbc");
        super.setName("customTokenRealm");
    }

    /**
     * 首先判断是不是自定义的jwtToken
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 做认证
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String)auth.getPrincipal();
        String account  = JwtUtil.getClaim(token,SecurityConsts.ACCOUNT);

        if (account == null) {
            throw new AuthenticationException("token invalid");
        }

        //实际项目用应从service中查找账户
        //BpUser bpUserInfo = userService.findUserByAccount(account);

        String bpUserInfo = getPasswordByUsername(account);
        if (bpUserInfo == null) {
            throw new AuthenticationException("BpUser didn't existed!");
        }

//        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
//        //服务端每次校验请求的Token有效后，同时比对Token中的时间戳与缓存中的RefreshToken时间戳是否一致，一致则判定Token有效
//        if (JwtUtil.verify(token) && cacheClient.exists(refreshTokenCacheKey)) {
//            String currentTimeMillisRedis = cacheClient.get(refreshTokenCacheKey);
//            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
//            if (JwtUtil.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
//                return new SimpleAuthenticationInfo(token, token, "customTokenRealm");
//            }
//        }
        //替换为:
        if (JwtUtil.verify(token)) {
            return new SimpleAuthenticationInfo(token, token, "shiroRealm");
        }
        throw new AuthenticationException("Token expired or incorrect.");
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
