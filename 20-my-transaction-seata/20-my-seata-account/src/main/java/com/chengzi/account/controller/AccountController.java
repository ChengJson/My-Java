package com.chengzi.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.account.mapper.AccountMapper;
import com.chengzi.dto.AccountDTO;
import com.chengzi.service.AccountService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper mapper;

    @RequestMapping("/getAllAccount")
    public Object getAllAccount(){
        List<Map<String, Object>> maps =accountService.getAllAccount();
        return maps;
    }

    @RequestMapping("/deleteAccountById")
    public Object deleteById(Integer id){
        int i = mapper.deleteById(id);
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<>());
        return maps;
    }

    @RequestMapping("/testAccountTransaction")
    @Transactional
    public Object testTransaction(Integer id){
        int i = mapper.deleteById(id);
        int j = i / 0;
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<>());
        return maps;
    }
    @RequestMapping("/decreaseAccount")
    @Transactional
    public Object decreaseAccount(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(12);
        accountDTO.setUserId("zhangsan");
        accountService.decreaseAccount(accountDTO);
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<>());
        return maps;
    }


}
