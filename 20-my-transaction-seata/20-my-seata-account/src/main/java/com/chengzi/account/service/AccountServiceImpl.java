package com.chengzi.account.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengzi.account.mapper.AccountMapper;
import com.chengzi.domain.Account;
import com.chengzi.domain.Order;
import com.chengzi.dto.AccountDTO;
import com.chengzi.enums.RspStatusEnum;
import com.chengzi.response.ObjectResponse;
import com.chengzi.service.AccountService;
import io.seata.spring.annotation.GlobalLock;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        int account = baseMapper.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (account > 0){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }

    @Override
    @GlobalLock
    @Transactional(rollbackFor = {Throwable.class})
    public void testGlobalLock() {
        baseMapper.testGlobalLock("1");
        System.out.println("Hi, i got lock, i will do some thing with holding this lock.");
    }
}
