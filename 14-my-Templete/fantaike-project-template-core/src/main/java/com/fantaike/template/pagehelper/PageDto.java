package com.fantaike.template.pagehelper;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PageDto
 * @Description: 分页实体类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:23
 * @Version: v1.0 文件初始创建
 */
@Data
public class PageDto implements Serializable {
    /**
     * 页码
     */
    private int page;
    /**
     * 容量
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 数据
     */
    private List list;
}
