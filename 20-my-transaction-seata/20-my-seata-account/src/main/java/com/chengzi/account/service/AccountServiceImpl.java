package com.chengzi.account.service;

import com.chengzi.account.mapper.AccountMapper;
import com.chengzi.service.AccountService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;
    @Override
    public void debit(String userId, int money) {

    }
}
