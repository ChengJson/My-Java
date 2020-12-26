package com.fantaike.template.service.setting;

import com.fantaike.template.domain.setting.SysLog;

import java.util.List;

/**
 * @ClassName: SysLogService
 * @Description: 系统日志服务接口
 * @Author: wuguizhen
 * @Date: 2019/7/11 11:07
 * @Version: v1.0 文件初始创建
 */
public interface SysLogService {

    /**
     * @Description: 新增方法
     * @param sysLog
     * @Date: 2019/7/11 11:10
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
	void insert(SysLog sysLog);
    
	/**
	 * @Description: 查询方法
	 * @param sysLog
	 * @Date: 2019/7/11 11:45
	 * @Author: wuguizhen
	 * @Return java.util.List<com.fantaike.template.domain.setting.SysLog>
	 * @Throws
	 */
    List<SysLog> list(SysLog sysLog);
	
}
