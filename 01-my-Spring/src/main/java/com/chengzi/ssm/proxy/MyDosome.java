package com.chengzi.ssm.proxy;

import com.chengzi.ssm.util.Myloger;

public class MyDosome implements Dosome {
    @Override
    public String dosome(String args) {
        Myloger.getLogger(this.getClass()).info("dosome方法执行");
        return args;
    }
}

