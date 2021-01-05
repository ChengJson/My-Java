package com.chengzi.account.service;
import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengzi.account.mapper.AccountMapper;
import com.chengzi.domain.Account;
import com.chengzi.domain.Order;
import com.chengzi.dto.AccountDTO;
import com.chengzi.enums.RspStatusEnum;
import com.chengzi.response.ObjectResponse;
import com.chengzi.service.AccountService;
import io.seata.spring.annotation.GlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    /**
     * 减去金额
     * @param accountDTO
     * @return
     */
    @Override
    @Transactional
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        int account = baseMapper.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount());
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

    /**
     * 获取所有账户
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllAccount() {
        List<Map<String, Object>> maps = accountMapper.selectMaps(new QueryWrapper<>());
        return maps;
    }
}
