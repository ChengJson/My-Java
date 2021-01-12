package com.chengzi.dto;

import java.io.Serializable;
import java.util.List;

/**
 *Desc:
 *@author:chengli
 *@date:2021/1/12 15:20
 */
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
