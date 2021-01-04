package com.chengzi.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengzi.domain.Account;
import com.chengzi.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/28 18:00
 */
public interface AccountMapper extends BaseMapper<Account> {


    int decreaseAccount(@Param("userId") String userId, @Param("amount") Double amount);

    int testGlobalLock(@Param("userId") String userId);

}
