package com.chengzi.storage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengzi.domain.Storage;
import com.chengzi.dto.CommodityDTO;
import com.chengzi.enums.RspStatusEnum;
import com.chengzi.response.ObjectResponse;
import com.chengzi.service.StorageService;
import com.chengzi.storage.mapper.StorageMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    /**
     * 执行减库存的操作
     * @param
     */
    @Override
    public ObjectResponse decreaseStorage(CommodityDTO commodityDTO) {
        int storage = baseMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (storage > 0){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }
}
