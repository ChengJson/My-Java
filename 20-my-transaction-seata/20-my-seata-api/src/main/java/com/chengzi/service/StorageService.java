package com.chengzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.domain.Storage;
import com.chengzi.dto.CommodityDTO;
import com.chengzi.response.ObjectResponse;

import java.util.List;
import java.util.Map;

public interface StorageService extends IService<Storage> {

    /**
     * 扣除存储数量
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);

    List<Map<String, Object>> getAllStorage();
}
