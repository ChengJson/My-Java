package com.chengzi.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account_tbl")
public class Account {
    private Integer id;
    private String userId;
    private Integer money;
}
