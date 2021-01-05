package com.chengzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.domain.Account;
import com.chengzi.dto.AccountDTO;
import com.chengzi.response.ObjectResponse;

import java.util.List;
import java.util.Map;

public interface AccountService extends IService<Account> {


    /**
     * 从用户账户中借出
     */
    ObjectResponse decreaseAccount(AccountDTO accountDTO);

    void testGlobalLock();

    List<Map<String, Object>> getAllAccount();
}
