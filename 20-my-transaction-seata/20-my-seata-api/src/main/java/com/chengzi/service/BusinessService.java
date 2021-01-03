package com.chengzi.service;

/**
 *
 * 主业务逻辑
 */
public interface BusinessService {

    /**
     * 采购
     */
    public void purchase(String userId, String commodityCode, int orderCount);
}
