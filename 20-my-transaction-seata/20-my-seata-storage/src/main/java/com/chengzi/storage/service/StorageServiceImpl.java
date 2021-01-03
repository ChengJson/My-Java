package com.chengzi.storage.service;

import com.chengzi.domain.Storage;
import com.chengzi.service.StorageService;
import com.chengzi.storage.mapper.StorageMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    /**
     * 执行减库存的操作
     * @param commodityCode
     * @param count
     */
    @Override
    public void deduct(String commodityCode, int count) {
        /**
         * 减库存
         */
        Storage storage = new Storage();
        storage.setCommodityCode(commodityCode);
        storage.setCount(count);
        storageMapper.updateById(storage);
    }
}
