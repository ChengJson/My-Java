package com.chengzi.domain;

import lombok.Data;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/28 17:59
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}