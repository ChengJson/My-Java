package com.chengzi.service;

import com.chengzi.dto.AccountDTO;
import com.chengzi.response.ObjectResponse;

public interface AccountService {


    /**
     * 从用户账户中借出
     */
    ObjectResponse decreaseAccount(AccountDTO accountDTO);

    void testGlobalLock();

}
