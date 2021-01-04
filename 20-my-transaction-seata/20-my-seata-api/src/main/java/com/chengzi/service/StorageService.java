package com.chengzi.service;

import com.chengzi.dto.CommodityDTO;
import com.chengzi.response.ObjectResponse;

public interface StorageService {

    /**
     * 扣除存储数量
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);
}
