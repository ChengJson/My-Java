package com.fantaike.template.service.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.domain.setting.SysUsr;
import com.fantaike.template.domain.setting.SysUsrRole;
import com.fantaike.template.dto.setting.UserDetailDTO;
import com.fantaike.template.exception.BusinessException;
import com.fantaike.template.exception.DBException;
import com.fantaike.template.mapper.setting.SysUsrMapper;
import com.fantaike.template.mapper.setting.SysUsrRoleMapper;
import com.fantaike.template.pagehelper.PageDto;
import com.fantaike.template.pagehelper.PageUtil;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.util.MenuFormatUtil;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @ClassName: SysUsrServiceImpl
 * @Description: 用户信息接口实现类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:07
 * @Version: v1.0 文件初始创建
 */
@Service()
public class SysUsrServiceImpl extends ServiceImpl<SysUsrMapper, SysUsr> implements SysUsrService {


    /** 用户角色关联表 */
    @Autowired
    private SysUsrRoleMapper sysUsrRoleMapper;
    
    /** 菜单信息表 */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * @Description: 修改用户信息
     * @param userDetailDTO 用户信息
     * @param roleIds 角色id列表，格式为，例： 1,2,3
     * @Date: 2019/7/8 19:56
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    /** 当方法抛出dbexception时进行回滚操作 */
    @Transactional(rollbackFor = DBException.class)
    @Override
    public JsonResponse updateUser(UserDetailDTO userDetailDTO, List<Long> roleIds) {
        //获取当前操作用户编号
        String optUsrId = userDetailDTO.getUpdateUsr();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户开始，用户信息：" + userDetailDTO.toString());
        String usrId = userDetailDTO.getUsrId();
        //查询修改前用户信息
        SysUsr oldUserInfo = getById(usrId);
        //查询结果为空
        if (ObjectIsNullUtil.isNullOrEmpty(oldUserInfo)) {
            CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户该用户编号不存在：" + usrId);
            //返回用户不存在
            return JsonResponse.response(ApiEnum.RSLT_CDE_002003);
        }
        //封装用户修改实体
        SysUsr updateInfo = new SysUsr();
        BeanUtils.copyProperties(userDetailDTO,updateInfo);
        updateInfo.setUsrId(usrId);
        updateInfo.setUpdateUsr(optUsrId);
        updateInfo.setUpdateTime(new Date());
        updateInfo.setUsrName(userDetailDTO.getUsrName());
        String oldPassword = oldUserInfo.getUsrPassword();
        String newPassword = userDetailDTO.getUsrPassword();
        //若新密码和旧密码不相同，则认为密码进行了修改，对新密码进行md5,并放到修改实体中进行密码修改
        if (!oldPassword.equals(newPassword)) {
            SimpleHash hash = new SimpleHash("md5", userDetailDTO.getUsrPassword(), userDetailDTO.getUsrId(), 2);
            String md5Password = hash.toHex();
            updateInfo.setUsrPassword(md5Password);
        }
        try {
            //用户表进行修改
            updateById(updateInfo);
            /**
             * 更新用户角色信息
             * 因为存在新增角色和删除角色两种操作，判断起来相对繁琐，
             * 所以此处修改前先删除该用户所对应用户权限表中数据再进行添加，由于存在有事物的操作sql执行可以进行回滚。
             */
            sysUsrRoleMapper.delete(new LambdaQueryWrapper<SysUsrRole>().eq(SysUsrRole::getUsrId, usrId));
            //添加用户角色关联表
            insertUserRoleInfo(roleIds, usrId);
        } catch (Exception e) {
            CommonLogger.error(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户异常", e);
            throw new DBException("用户修改失败");
        }
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户信息完成");
        return JsonResponse.success("用户修改成功");
    }

    /**
     * @Description: 添加用户
     * @param userDetailDTO 用户信息
     * @param roleIds 角色id列表，格式为，例： 1,2,3
     * @Date: 2019/7/8 19:56
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    /** 当方法抛出dbexception时进行回滚操作 */
    @Transactional(rollbackFor = DBException.class)
    @Override
    public JsonResponse createUser(UserDetailDTO userDetailDTO, List<Long> roleIds) throws BusinessException {
        //获取当前操作用户编号
        String optUsr = userDetailDTO.getUpdateUsr();
        CommonLogger.info(optUsr, BusinessNameConstant.SETTING_USER, "添加用户开始，用户信息：" + userDetailDTO.toString());
        String usrId = userDetailDTO.getUsrId();
        //查询用户编号是否已存在
        SysUsr targetSysUsr = getById(usrId);
        //查询结果不为空
        if (ObjectIsNullUtil.notNullOrEmpty(targetSysUsr)) {
            CommonLogger.info(optUsr, BusinessNameConstant.SETTING_USER, "添加用户失败，用户已存在");
            //返回用户已存在
            return JsonResponse.response(ApiEnum.RSLT_CDE_002002);
        }
        //封装用户表实体
        SysUsr insertSysUsr = new SysUsr();
        BeanUtils.copyProperties(userDetailDTO,insertSysUsr);
        insertSysUsr.setUsrId(usrId);
        insertSysUsr.setCreateTime(new Date());
        insertSysUsr.setCreateUsr(optUsr);
        insertSysUsr.setStatus(CommonEnum.STAT_ACTIVE.getCode());
        SimpleHash hash = new SimpleHash("md5", userDetailDTO.getUsrPassword(), userDetailDTO.getUsrId(), 2);
        String md5Password = hash.toHex();
        //生成密码摘要，盐为用户编号
        insertSysUsr.setUsrPassword(md5Password);
        try {
            //添加用户到用户表
            save(insertSysUsr);
            insertUserRoleInfo(roleIds, usrId);
        } catch (Exception e) {
            CommonLogger.error(optUsr, BusinessNameConstant.SETTING_USER, "添加用户操作失败", e);
            throw new DBException("用户添加失败");
        }
        CommonLogger.info(optUsr, BusinessNameConstant.SETTING_USER, "添加用户信息完成");
        return JsonResponse.success("用户添加成功");
    }

    /**
     * @Description: 获取用户详情
     * @param usrId 用户编号
     * @Date: 2019/7/8 19:55
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public JsonResponse getUserDetail(String usrId) {
        CommonLogger.info("", BusinessNameConstant.SETTING_USER, "查询用户详细信息，用户编号：" + usrId);
        //根据用户编号查询用户
        SysUsr usrInfo = getById(usrId);
        if (ObjectIsNullUtil.isNullOrEmpty(usrInfo)) {
            CommonLogger.info("", BusinessNameConstant.SETTING_USER, "修改用户该用户编号不存在：" + usrId);
            //返回用户不存在
            return JsonResponse.response(ApiEnum.RSLT_CDE_002003);
        }
        //封装用户详情信息
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        //通过反射的方式 复制SysRole 属性的值 到 RoleDetailDTO
        BeanUtils.copyProperties(usrInfo, userDetailDTO);
        //通过用户编号查询该用户所有生效的角色
        List<SysRole> roleList = listRoleByUsrId(usrId);
        userDetailDTO.setRoleList(roleList);
        return JsonResponse.success(userDetailDTO);
    }

    /**
     * @Description: 获取角色列表，并根据页码和每页的容量进行分页查询
     * @param selectParam 用户查询参数
     * @param page 页码
     * @param pageSize 页容量
     * @Date: 2019/7/8 19:55
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public JsonResponse listObjectsByPage(SysUsr selectParam, Integer page, Integer pageSize) {
        //设置分页属性 页码和页容量，该设置下面需要紧跟着查询
        PageUtil.setPageInfo(page, pageSize);
        PageDto pageInfo = PageUtil.getPageDto(list(new LambdaQueryWrapper<>(selectParam)));
        //进行查询并返回结果
        return JsonResponse.success(pageInfo);
    }

    /**
     * @Description: 将用户状态置位生效和失效
     * @param usrId 角色id
     * @param sts 修改成的状态 取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @param optUsrId 操作用户id
     * @Date: 2019/7/8 19:48
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public JsonResponse changeStatus(String usrId, String sts, String optUsrId) {
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户编号：" + usrId + " 所对应状态为：" + sts);
        SysUsr updateInfo = new SysUsr();
        updateInfo.setUsrId(usrId);
        updateInfo.setUpdateUsr(optUsrId);
        updateInfo.setUpdateTime(new Date());
        updateInfo.setStatus(sts);
        updateById(updateInfo);
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户编号：" + usrId + " 所对应状态为：" + sts + "完成");
        return JsonResponse.success("用户状态修改成功");
    }

    /**
     * @Description: 根据用户id进行查询
     * @param usrId 用户id
     * @Date: 2019/7/8 20:05
     * @Author: wuguizhen
     * @Return com.fantaike.template.domain.setting.SysUsr
     * @Throws
     */
    @Override
    public SysUsr getByUsrId(String usrId) {
        return getById(usrId);
    }

    /**
     * @return java.util.List<com.fantaike.template.domain.setting.SysRole>
     * @Author wugz
     * @Description 根据用户id查询其角色列表
     * @Date 2019/6/21 13:40
     * @Param [usrId]
     */
    @Override
    public List<SysRole> listRoleByUsrId(String usrId) {
        return sysUsrRoleMapper.listRoleByUsrId(usrId, CommonEnum.STAT_ACTIVE.getCode());
    }

    /**
     * @Description: 获取用户的生效的菜单信息
     * @param usrId 用户id
     * @Date: 2019/7/8 19:58
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public List<SysMenu> listMenuByUsrId(String usrId) {
        return sysUsrRoleMapper.listMenuByUsrId(usrId, CommonEnum.STAT_ACTIVE.getCode());
    }

    /**
     * @Description: 获取用户的生效的菜单信息并格式化成树结构
     * @param usrId 用户id
     * @Date: 2019/7/8 20:06
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    @Override
    public List<SysMenu> listMenuByUsrIdFormat(String usrId) {
        List<SysMenu> menuList = sysUsrRoleMapper.listMenuByUsrId(usrId, CommonEnum.STAT_ACTIVE.getCode());
        return MenuFormatUtil.formatToTreeList(menuList);
    }

    /**
     * @Description: 插入用户角色表
     * @param roleIds 角色id列表
     * @param usrId 用户id
     * @Date: 2019/7/8 20:11
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    private void insertUserRoleInfo(List<Long> roleIds, String usrId) {
        //如果角色列表不为空，则循环roleList封装用户角色信息并入库
        if (ObjectIsNullUtil.notNullOrEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                //封装用户角色信息
                SysUsrRole sysUsrRole = new SysUsrRole();
                sysUsrRole.setRoleId(roleId);
                sysUsrRole.setUsrId(usrId);
                sysUsrRoleMapper.insert(sysUsrRole);
            });

        }
    }

}
