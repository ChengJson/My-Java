package com.chengzi.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_tbl")
public class Order {
    private Integer id;
    private String userId;
    private String  commodityCode;
    private Integer count;
    private Integer money;
}
