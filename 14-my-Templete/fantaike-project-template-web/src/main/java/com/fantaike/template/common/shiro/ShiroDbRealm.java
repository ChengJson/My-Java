package com.fantaike.template.common.shiro;


import org.apache.dubbo.config.annotation.Reference;
import com.fantaike.template.common.shiro.security.JwtToken;
import com.fantaike.template.common.shiro.security.JwtUtil;
import com.fantaike.template.common.shiro.security.SecurityConsts;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.domain.login.ShiroAccountInfo;
import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.domain.setting.SysUsr;
import com.fantaike.template.service.setting.SysMenuService;
import com.fantaike.template.service.setting.SysUsrService;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.Assert;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName: ShiroDbRealm
 * @Description: 自定义shiro登录实现类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:27
 * @Version: v1.0 文件初始创建
 */
public class ShiroDbRealm extends AuthorizingRealm {
    
    /** 引用用户服务 */
    @Reference
    private SysUsrService sysUsrService;
    
    /** 引用菜单服务 */
    @Reference
    private SysMenuService sysMenuService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    
    /**
     * @Description: 重写该方法，实现登陆验证
     * @param authcToken
     * @Date: 2019/7/8 20:27
     * @Author: wuguizhen
     * @Return org.apache.shiro.authc.AuthenticationInfo
     * @Throws
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String token = (String)authcToken.getPrincipal();
        String account  = JwtUtil.getClaim(token, SecurityConsts.ACCOUNT);

        if (account == null) {
            throw new AuthenticationException("token invalid");
        }
        //验证token
        if (JwtUtil.verify(token)) {
            //根据用户编号查询用户信息
            SysUsr userInfo = sysUsrService.getByUsrId(account);
            ShiroAccountInfo shiroUserInfo = new ShiroAccountInfo(userInfo);
            shiroUserInfo.setRoleList(sysUsrService.listRoleByUsrId(account));
            return new SimpleAuthenticationInfo(shiroUserInfo, token, getName());
        }
        throw new AuthenticationException("Token expired or incorrect.");

    }

    /**
     * @Description: 重写该方法限制用户访问权限
     * @param principals
     * @Date: 2019/7/8 20:35
     * @Author: wuguizhen
     * @Return org.apache.shiro.authz.AuthorizationInfo
     * @Throws
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroAccountInfo shiroUserInfo = (ShiroAccountInfo) principals.getPrimaryPrincipal();
        Assert.notNull(shiroUserInfo, "找不到principals中的SessionVariable");
        List<SysRole> roleList = shiroUserInfo.getRoleList();
        if (ObjectIsNullUtil.notNullOrEmpty(roleList)) {
            List<String> roleIds = roleList.stream().map(role -> role.getRoleId().toString()).collect(Collectors.toList());
            info.addRoles(roleIds);
        }
        //查询该用户所属的权限，到按钮
        List<SysMenu> menuList = sysUsrService.listMenuByUsrId(shiroUserInfo.getUsrId());
        if (ObjectIsNullUtil.notNullOrEmpty(menuList)) {
            List<String> perms = menuList.stream()
                    //过滤是按钮级别的菜单
                    .filter(menu -> Integer.valueOf(CommonEnum.MENU_TYPE_BUTTON.getCode()).equals(menu.getType()))
                    //获取权限list
                    .map(SysMenu::getPerms).collect(Collectors.toList());
            info.addStringPermissions(perms);
        }
        return info;
    }
}
