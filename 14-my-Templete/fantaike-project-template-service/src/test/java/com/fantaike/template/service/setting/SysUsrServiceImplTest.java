package com.fantaike.template.service.setting;

import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUsrServiceImplTest {

    @Autowired
    private SysUsrService sysUsrService;

    @Test
    public void listMenuByUsrId() {
        //查询该用户所属的权限，到按钮
        List<SysMenu> menuList = sysUsrService.listMenuByUsrId("admin");
        if(ObjectIsNullUtil.notNullOrEmpty(menuList)){
            List<String> perms = menuList.stream()
                    //过滤是按钮级别的菜单
                    .filter(menu -> CommonEnum.MENU_TYPE_BUTTON.getCode().equals(menu.getType()))
                    //获取权限list
                    .map(SysMenu :: getPerms).collect(Collectors.toList());
            System.out.println(perms);
        }
        System.out.println(CommonEnum.MENU_TYPE_BUTTON.getCode());
    }
}