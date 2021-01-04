package com.chengzi.service;

import com.chengzi.dto.BusinessDTO;
import com.chengzi.dto.OrderDTO;
import com.chengzi.response.ObjectResponse;

/**
 *
 * 主业务逻辑
 */
public interface BusinessService {

    /**
     * 采购
     */
    public ObjectResponse<OrderDTO> purchase(BusinessDTO businessDTO);
}
