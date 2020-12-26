package com.fantaike.template.service.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.domain.setting.SysRoleAuth;
import com.fantaike.template.dto.setting.RoleDetailDTO;
import com.fantaike.template.exception.BusinessException;
import com.fantaike.template.exception.DBException;
import com.fantaike.template.mapper.setting.SysRoleAuthMapper;
import com.fantaike.template.mapper.setting.SysRoleMapper;
import com.fantaike.template.pagehelper.PageDto;
import com.fantaike.template.pagehelper.PageUtil;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @ClassName: SysRoleServiceImpl
 * @Description: 角色信息表接口实现类
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:50
 * @Version: v1.0 文件初始创建
 */
@Service()
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /** 角色权限关联表 */
    @Autowired
    private SysRoleAuthMapper sysRoleAuthMapper;

    /**
     * @Description: 修改角色，修改角色信息表和角色权限关联表
     * @param roleDetailDTO 角色相信信息
     * @param authIds 权限id列表 格式为 例： 1,2,3
     * @Date: 2019/7/8 19:47
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    /** 当方法抛出dbexception时进行回滚操作 */
    @Transactional(rollbackFor = DBException.class)
    @Override
    public JsonResponse updateRole(RoleDetailDTO roleDetailDTO, List<Long> authIds) throws BusinessException {
        CommonLogger.info(roleDetailDTO.getUpdateUsr(), BusinessNameConstant.SETTING_ROLE, "修改角色开始，角色信息:" + roleDetailDTO.toString());
        String optUsr = roleDetailDTO.getUpdateUsr();
        Long roleId = roleDetailDTO.getRoleId();
        //根据角色id查询
        SysRole targetRoleInfo = getById(roleId);
        //如果查询结果为空 返回角色不存在
        if (ObjectIsNullUtil.isNullOrEmpty(targetRoleInfo)) {
            CommonLogger.info(optUsr, BusinessNameConstant.SETTING_ROLE, "角色不存在，角色id：" + roleId);
            //返回角色不存在
            return JsonResponse.response(ApiEnum.RSLT_CDE_002005);
        }

        //封装角色修改实体
        SysRole updateRoleInfo = new SysRole();
        updateRoleInfo.setUpdateTime(new Date());
        updateRoleInfo.setRoleName(roleDetailDTO.getRoleName());
        updateRoleInfo.setUpdateUsr(roleDetailDTO.getUpdateUsr());
        updateRoleInfo.setRoleId(roleId);
        try {
            //根据主键修改角色信息
            updateById(updateRoleInfo);
            /**
             * 更新角色权限信息
             * 权限和角色通过中间表进行关联，修改时存在权限新增、删除两种情况判断起来相对繁琐，
             * 所以此处修改前先删除该角色所对应角色权限表中数据再进行添加，由于存在有事物的操作sql执行可以进行回滚。
             */
            sysRoleAuthMapper.delete(new LambdaQueryWrapper<SysRoleAuth>().eq(SysRoleAuth::getRoleId, roleId));
            //删除后根据角色详情数据添加新的权限
            insertRoleAuth(authIds, roleId);
        } catch (Exception e) {
            CommonLogger.error(roleDetailDTO.getUpdateUsr(), BusinessNameConstant.SETTING_ROLE, "修改角色异常", e);
            throw new DBException("角色信息修改失败");
        }
        CommonLogger.info(roleDetailDTO.getUpdateUsr(), BusinessNameConstant.SETTING_ROLE, "修改角色信息完成");
        return JsonResponse.success("角色修改成功");
    }

    /**
     * @Description: 新增角色，保存角色信息表和角色权限关联表
     * @param roleDetailDTO 角色相信信息
     * @param authIds 权限id列表 格式为 例： 1,2,3
     * @Date: 2019/7/8 19:47
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    /** 当方法抛出dbexception时进行回滚操作 */
    @Transactional(rollbackFor = DBException.class)
    @Override
    public JsonResponse createRole(RoleDetailDTO roleDetailDTO, List<Long> authIds) throws BusinessException {
        String optUsr = roleDetailDTO.getUpdateUsr();
        CommonLogger.info(optUsr, BusinessNameConstant.SETTING_ROLE, "新增角色信息开始：" + roleDetailDTO.toString());
        String roleName = roleDetailDTO.getRoleName();
        //查询角色名称是否已存在
        SysRole existedSysRole = getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleName, roleName));
        //如果已存在这返回 角色已存在
        if (ObjectIsNullUtil.notNullOrEmpty(existedSysRole)) {
            CommonLogger.info(optUsr, BusinessNameConstant.SETTING_ROLE, "新增角色，角色已存在，角色名称：" + roleName);
            return JsonResponse.response(ApiEnum.RSLT_CDE_002004);
        }
        //封装角色新增实体
        SysRole insertRoleInfo = new SysRole();
        insertRoleInfo.setRoleName(roleName);
        insertRoleInfo.setRoleRmk(roleDetailDTO.getRoleRmk());
        insertRoleInfo.setStatus(CommonEnum.STAT_ACTIVE.getCode());
        insertRoleInfo.setCreateTime(new Date());
        insertRoleInfo.setCreateUsr(optUsr);
        try {
            //添加角色信息到角色表
            save(insertRoleInfo);
            //获取插入后的自增id
            long roleId = insertRoleInfo.getRoleId();
            //解析角色权限信息，添加到角色权限关联表中
            insertRoleAuth(authIds, roleId);
        } catch (Exception e) {
            CommonLogger.error(optUsr, BusinessNameConstant.SETTING_ROLE, "角色信息保存失败", e);
            throw new DBException("角色信息添加失败");
        }
        CommonLogger.info(optUsr, BusinessNameConstant.SETTING_ROLE, "新增角色信息完成");
        return JsonResponse.success("角色添加成功");
    }

    /**
     * @Description: 获取角色列表，并根据页码和每页的容量进行分页查询
     * @param roleName 角色名称，不为空时按角色名称为条件查询，否则不进行条件查询
     * @param page 页码
     * @param pageSize 页容量
     * @Date: 2019/7/8 19:45
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public JsonResponse listObjectsByPage(String roleName, Integer page, Integer pageSize) {
        //添加查询条件实体，roleName为空不进行条件查询
        SysRole selectParam = new SysRole();
        if (ObjectIsNullUtil.notNullOrEmpty(roleName)) {
            selectParam.setRoleName(roleName);
        }
        //设置分页属性 页码和页容量，该设置下面需要紧跟着查询
        PageUtil.setPageInfo(page, pageSize);
        PageDto pageInfo = PageUtil.getPageDto(list(new LambdaQueryWrapper<>(selectParam)));
        //进行查询并返回结果
        return JsonResponse.success(pageInfo);
    }

    /**
     * @Description: 获取角色详情，关联权限表获取该角色的所有权限
     * @param roleId 角色id 不能为空
     * @Date: 2019/7/8 19:46
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public JsonResponse getRoleDetail(Long roleId) {
        //通过角色id进行查询 getById是mybatis-plus 封装的方法
        SysRole roleInfo = getById(roleId);
        //若查询结果为空,返回角色不存在
        if (ObjectIsNullUtil.isNullOrEmpty(roleInfo)) {
            CommonLogger.info("", BusinessNameConstant.SETTING_ROLE, "角色不存在，角色id：" + roleId);
            //返回用户不存在
            return JsonResponse.response(ApiEnum.RSLT_CDE_002005);
        }
        //通过角色编号查询该角色对应生效的权限信息
        List<SysAuth> authList = sysRoleAuthMapper.listByRoleId(roleId, CommonEnum.STAT_ACTIVE.getCode());

        //封装返回数据
        RoleDetailDTO roleDetailDTO = new RoleDetailDTO();
        roleDetailDTO.setAuthList(authList);
        //通过反射的方式 复制SysRole 属性的值 到 RoleDetailDTO
        BeanUtils.copyProperties(roleInfo, roleDetailDTO);
        return JsonResponse.success(roleDetailDTO);
    }

    /**
     * @Description: 将角色状态置位生效和失效
     * @param roleId 角色id
     * @param sts 修改成的状态 取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @Date: 2019/7/8 19:48
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Override
    public JsonResponse changeStatus(Long roleId, String sts, String optUsrId) {
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_ROLE, "修改角色编号：" + roleId + " 所对应角色状态为：" + sts);
        SysRole updateInfo = new SysRole();
        updateInfo.setRoleId(roleId);
        updateInfo.setUpdateUsr(optUsrId);
        updateInfo.setUpdateTime(new Date());
        updateInfo.setStatus(sts);
        updateById(updateInfo);
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_ROLE, "修改角色编号：" + roleId + " 所对应角色状态为：" + sts + "完成");
        return JsonResponse.success("角色状态修改成功");
    }

    /**
     * @Description: 添加角色权限关联表
     * @param authList 权限id列表
     * @param roleId 角色id
     * @Date: 2019/7/8 19:53
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    private void insertRoleAuth(List<Long> authList, Long roleId) {
        //判断权限id是否为空，若不为空则循环添加到角色权限关联表中
        if (ObjectIsNullUtil.notNullOrEmpty(authList)) {
            authList.stream().forEach(authIds -> {
                SysRoleAuth sysRoleAuth = new SysRoleAuth();
                sysRoleAuth.setRoleId(roleId);
                sysRoleAuth.setAuthId(authIds);
                sysRoleAuthMapper.insert(sysRoleAuth);
            });
        }
    }

}
