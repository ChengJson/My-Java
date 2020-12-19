package com.chengzi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "url")
public class ConfigInfo {

    private String alipay;
    private String weixinpay;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWeixinpay() {
        return weixinpay;
    }

    public void setWeixinpay(String weixinpay) {
        this.weixinpay = weixinpay;
    }

    @Override
    public String toString() {
        return "ConfigInfo{" +
                "alipay='" + alipay + '\'' +
                ", weixinpay='" + weixinpay + '\'' +
                '}';
    }
}
