package com.fantaike.template.service.setting;


import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.mapper.setting.SysMenuMapper;
import com.fantaike.template.util.MenuFormatUtil;

import java.util.List;

/**
 * @ClassName: SysMenuServiceImpl
 * @Description: 菜单表接口实现类
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:51
 * @Version: v1.0 文件初始创建
 */
@Service()
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * @Description: 查询所有生效的菜单，并进行格式化成树状结构
     * @param
     * @Date: 2019/7/8 19:40
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    @Override
    public List<SysMenu> getMenuTreeList() {
        //查询条件为 status = CommonEnum.STAT_ACTIVE.getCode()
        return MenuFormatUtil.formatToTreeList(list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, CommonEnum.STAT_ACTIVE.getCode())));
    }
}
