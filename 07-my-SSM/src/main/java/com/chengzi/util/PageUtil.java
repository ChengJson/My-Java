package com.chengzi.util;

/**
 *Desc:
 *@author:chengli
 *@date:2021/1/12 15:19
 */

import com.chengzi.dto.PageDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName: PageUtil
 * @Description: 分页操作工具类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:15
 * @Version: v1.0 文件初始创建
 */
public class PageUtil {

    /**
     * @Description: 分页前设置分页信息
     * @param page 页码
     * @param pageSize 容量
     * @Date: 2019/7/8 20:15
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    public static void setPageInfo(Integer page, Integer pageSize) {
        //设置分页
//        if (ObjectIsNullUtil.isNullOrEmpty(page)) {
//            page = 1;
//        }
//        if (ObjectIsNullUtil.isNullOrEmpty(pageSize)) {
//            pageSize = 10;
//        }
        PageHelper.startPage(page, pageSize);
    }

    /**
     * @Description: 设置分页数据
     * @param data 分页数据
     * @Date: 2019/7/8 20:15
     * @Author: wuguizhen
     * @Return com.fantaike.template.pagehelper.PageDto
     * @Throws
     */
    public static PageDto getPageDto(List data) {
        PageInfo pageInfo = new PageInfo(data);
        PageDto pageDto = new PageDto();
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(pageInfo.getList());
        return pageDto;
    }
}
