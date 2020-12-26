package com.fantaike.template.common.shiro.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtUtilTest {

    @Test
    public void init() {
    }

    @Test
    public void verify() {
    }

    @Test
    public void getClaim() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjdXJyZW50VGltZU1pbGxpcyI6IjE1NjMxNzY3Nzk0MTciLCJleHAiOjE1NjMyNjMxNzksImFjY291bnQiOiJhZG1pbiJ9.GKzfa0fp1_fBIaAm9PdYdXuu9tRH0HNkgw7Z-V9QBZ0";
        System.out.println(JwtUtil.getClaim(token, SecurityConsts.ACCOUNT));
    }

    @Test
    public void sign() {
    }
}