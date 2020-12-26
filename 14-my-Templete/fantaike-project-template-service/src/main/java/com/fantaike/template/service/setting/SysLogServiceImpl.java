package com.fantaike.template.service.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantaike.template.domain.setting.SysLog;
import com.fantaike.template.mapper.setting.SysLogMapper;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;


/**
 * @ClassName: SysLogServiceImpl
 * @Description: 系统日志服务实现类
 * @Author: wuguizhen
 * @Date: 2019/7/11 11:12
 * @Version: v1.0 文件初始创建
 */
@Service()
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService {

	/**
	 * @Description: 新增方法
	 * @param sysLog
	 * @Date: 2019/7/11 11:10
	 * @Author: wuguizhen
	 * @Return void
	 * @Throws
	 */
	@Override
	public void insert(SysLog sysLog) {
		save(sysLog);
	}

	/**
	 * @Description: 查询方法
	 * @param sysLog
	 * @Date: 2019/7/11 11:45
	 * @Author: wuguizhen
	 * @Return java.util.List<com.fantaike.template.domain.setting.SysLog>
	 * @Throws
	 */
	@Override
	public List<SysLog> list(SysLog sysLog) {
		return list(new LambdaQueryWrapper<>(sysLog));
	}
}
