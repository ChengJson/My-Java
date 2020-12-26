package com.fantaike.template.service.setting;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.domain.setting.SysAuthMenu;
import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.dto.setting.AuthDetailDTO;
import com.fantaike.template.exception.DBException;
import com.fantaike.template.mapper.setting.SysAuthMapper;
import com.fantaike.template.mapper.setting.SysAuthMenuMapper;
import com.fantaike.template.pagehelper.PageDto;
import com.fantaike.template.pagehelper.PageUtil;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.util.MenuFormatUtil;
import com.fantaike.template.util.ObjectIsNullUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: SysAuthServiceImpl
 * @Description: 权限信息表接口实现类
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:29
 * @Version: v1.0 文件初始创建
 */
@Service
public class SysAuthServiceImpl extends ServiceImpl<SysAuthMapper,SysAuth> implements SysAuthService {
    
    /** 权限菜单关联表 */
	@Autowired
	private SysAuthMenuMapper sysAuthMenuMapper;

	/**
	 * @Description: 获取权限列表，根据分页数据进行查询
	 * @param selectParam 查询条件
	 * @param page 分页页码
	 * @param pageSize 分页容量
	 * @Date: 2019/7/5 18:28
	 * @Author: wuguizhen
	 * @Return com.fantaike.template.api.JsonResponse
	 * @Throws
	 */
	@Override
	public JsonResponse listObjectsByPage(SysAuth selectParam,Integer page, Integer pageSize) {
		//设置分页属性 页码和页容量，该设置下面需要紧跟着查询
		PageUtil.setPageInfo(page, pageSize);
        //进行查询并返回结果
		PageDto pageInfo = PageUtil.getPageDto(list(new LambdaQueryWrapper<>(selectParam)));
		return JsonResponse.success(pageInfo);
	}

	/**
	 * @Description: 查询权限详情
	 * @param authId 权限id
	 * @Date: 2019/7/5 18:29
	 * @Author: wuguizhen
	 * @Return com.fantaike.template.api.JsonResponse
	 * @Throws
	 */
	@Override
	public JsonResponse getAuthDetail(Long authId) {
		//根据权限id查询权限信息
		SysAuth sysAuth = getById(authId);
		//如果为空则返回权限不存在
		if(ObjectIsNullUtil.isNullOrEmpty(sysAuth)){
			CommonLogger.info("",BusinessNameConstant.SETTING_AUTH,"权限不存在，权限id："+authId);
			//返回权限不存在
			return JsonResponse.response(ApiEnum.RSLT_CDE_002007);
		}
		//根据权限id查询 该权限所对应的所有菜单信息
		List<SysMenu> menuList = sysAuthMenuMapper.listMenuByAuthId(authId,CommonEnum.STAT_ACTIVE.getCode());
		AuthDetailDTO authDetailDTO = new AuthDetailDTO();
		//格式化为树状结构
		authDetailDTO.setMenuList(MenuFormatUtil.formatToTreeList(menuList));
		//通过反射的方式 复制SysRole 属性的值 到 RoleDetailDTO
		BeanUtils.copyProperties(sysAuth, authDetailDTO);
		return JsonResponse.success(authDetailDTO);
	}

	/**
	 * @Description: 权限保存 保存权限信息表 和 权限菜单关联表
	 * @param authDetailDTO 需要添加的权限详细信息
	 * @param menuIds 需要添加的权限所属的菜单id 例：1,2,3
	 * @Date: 2019/7/5 18:29
	 * @Author: wuguizhen
	 * @Return com.fantaike.template.api.JsonResponse
	 * @Throws
	 */
	/** 当方法抛出dbexception时进行回滚操作 */
	@Transactional(rollbackFor = DBException.class)
	@Override
	public JsonResponse createAuth(AuthDetailDTO authDetailDTO,List<Long> menuIds) {
		//获取当前执行添加操作用户
	    String optUsr = authDetailDTO.getUpdateUsr();
		CommonLogger.info(optUsr,BusinessNameConstant.SETTING_AUTH,"新增权限信息开始："+ authDetailDTO.toString() );
		String authName = authDetailDTO.getAuthName();
		//查询权限名称是否已存在
		SysAuth existedSysAuth = getOne(new LambdaQueryWrapper<SysAuth>().eq(SysAuth :: getAuthName,authName));
		//如果已存在这返回 权限已存在
		if(ObjectIsNullUtil.notNullOrEmpty(existedSysAuth)){
			CommonLogger.info(optUsr,BusinessNameConstant.SETTING_AUTH,"新增权限，权限已存在，权限名称："+ authName);
			return JsonResponse.response(ApiEnum.RSLT_CDE_002006);
		}
		//封装权限新增实体
		SysAuth insertAuthInfo = new SysAuth();
		insertAuthInfo.setAuthName(authName);
		insertAuthInfo.setStatus(CommonEnum.STAT_ACTIVE.getCode());
		insertAuthInfo.setCreateTime(new Date());
		insertAuthInfo.setCreateUsr(optUsr);
		try {
			//添加角色信息到角色表
			save(insertAuthInfo);
			//获取插入后的自增id
			long authId = insertAuthInfo.getAuthId();
			//解析角色权限信息，添加到角色权限关联表中
			insertAuthMenu(menuIds,authId);
		} catch (Exception e){
			CommonLogger.error(optUsr,BusinessNameConstant.SETTING_AUTH,"权限信息保存失败",e);
			throw new DBException("权限信息添加失败");
		}
		CommonLogger.info(optUsr,BusinessNameConstant.SETTING_AUTH,"新增权限信息完成");
		return JsonResponse.success("权限添加成功");
	}

	/**
	 * @Description: 修改权限信息
	 * @param authDetailDTO 需要修改的权限详情
	 * @param menuIds 需要修改的权限所对应的菜单id  例：1,2,3
	 * @Date: 2019/7/5 18:31
	 * @Author: wuguizhen
	 * @Return com.fantaike.template.api.JsonResponse
	 * @Throws
	 */
    /** 当方法抛出dbexception时进行回滚操作 */
    @Transactional(rollbackFor = DBException.class)
	@Override
	public JsonResponse updateRole(AuthDetailDTO authDetailDTO,List<Long> menuIds) {
		String optUsr = authDetailDTO.getUpdateUsr();
		CommonLogger.info(optUsr,BusinessNameConstant.SETTING_AUTH,"修改权限开始，权限信息:"+ authDetailDTO.toString());
		Long authId = authDetailDTO.getAuthId();
		//根据权限id查询 修改目标权限信息
		SysAuth targetAuthInfo = getById(authId);
		if(ObjectIsNullUtil.isNullOrEmpty(targetAuthInfo)){
			CommonLogger.info(optUsr,BusinessNameConstant.SETTING_AUTH,"权限不存在，权限id："+authId);
			//返回权限不存在
			return JsonResponse.response(ApiEnum.RSLT_CDE_002007);
		}

		//封装角色修改实体
		SysAuth updateAuthInfo = new SysAuth();
		updateAuthInfo.setUpdateTime(new Date());
		updateAuthInfo.setAuthName(authDetailDTO.getAuthName());
		updateAuthInfo.setUpdateUsr(authDetailDTO.getUpdateUsr());
		updateAuthInfo.setAuthId(authId);
		try{
			//根据主键修改角色信息
			updateById(updateAuthInfo);
			if(ObjectIsNullUtil.notNullOrEmpty(menuIds)){
				/**
				 * 更新权限信息
				 * 权限和菜单通过中间表进行关联，修改时存在菜单新增、删除两种情况判断起来相对繁琐，
				 * 所以此处修改前先删除该角色所对应角色权限表中数据再进行添加，由于存在有事物的操作sql执行可以进行回滚。
				 */
				sysAuthMenuMapper.delete(new LambdaQueryWrapper<SysAuthMenu>().eq(SysAuthMenu :: getAuthId,authId));
				//删除后根据角色详情数据添加新的权限
				insertAuthMenu(menuIds,authId);
			}
		}catch (Exception e){
			CommonLogger.error(optUsr,BusinessNameConstant.SETTING_AUTH,"修改权限异常",e);
			throw new DBException("角色信息修改失败");
		}
		CommonLogger.info(optUsr,BusinessNameConstant.SETTING_AUTH,"修改权限信息完成");
		return JsonResponse.success("角色修改成功");
	}

	/**
	 * @Description:将权限状态置位生效和失效
	 * @param authId 权限编号
	 * @param sts 权限状态,取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
	 * @Date: 2019/7/5 18:32
	 * @Author: wuguizhen
	 * @Return com.fantaike.template.api.JsonResponse
	 * @Throws
	 */
	@Override
	public JsonResponse changeStatus(Long authId, String sts, String optUsrId) {
		CommonLogger.info(optUsrId,BusinessNameConstant.SETTING_AUTH,"修改权限编号："+authId+" 所对应权限状态为：" + sts );
		SysAuth updateInfo = new SysAuth();
		updateInfo.setAuthId(authId);
		updateInfo.setUpdateUsr(optUsrId);
		updateInfo.setUpdateTime(new Date());
		updateInfo.setStatus(sts);
		updateById(updateInfo);
		CommonLogger.info(optUsrId,BusinessNameConstant.SETTING_AUTH,"修改权限编号："+authId+" 所对应权限状态为：" + sts +"完成");
		return JsonResponse.success("权限状态修改成功");
	}

	/**
	 * @Description: 插入权限菜单关联表
	 * @param menuIds 菜单id列表
     * @param authId 权限id
	 * @Date: 2019/7/8 19:36
	 * @Author: wuguizhen
	 * @Return void
	 * @Throws
	 */
	private void insertAuthMenu(List<Long> menuIds, long authId) {
	    //循环添加到权限菜单关联表中
		menuIds.stream().forEach(menuId -> {
			SysAuthMenu sysAuthMenu = new SysAuthMenu();
			sysAuthMenu.setAuthId(authId);
			sysAuthMenu.setMenuId(menuId);
			sysAuthMenuMapper.insert(sysAuthMenu);
		});
	}
}
