package com.fantaike.template.service.setting;

import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.CommonEnum;
import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.dto.setting.AuthDetailDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysAuthServiceImplTest {

    @Autowired
    private SysAuthService sysAuthService;

    @Test
    public void listObjectsByPage() {
        JsonResponse response = sysAuthService.listObjectsByPage(new SysAuth(),1,10);
        Assert.assertEquals(ApiEnum.RSLT_CDE_999999.getCode(),response.getMessageCode());
        Assert.assertNotNull(response.getData());
    }

    @Test
    public void getAuthDetail() {
        JsonResponse response = sysAuthService.getAuthDetail(1L);
        Assert.assertEquals(ApiEnum.RSLT_CDE_000000.getCode(),response.getMessageCode());
        Assert.assertNotNull(response.getData());
    }

    @Test
    public void createAuth() {
        AuthDetailDTO authDetailDTO = new AuthDetailDTO();
        authDetailDTO.setAuthName("单测权限");
        authDetailDTO.setUpdateUsr("test");
        JsonResponse response = sysAuthService.createAuth(authDetailDTO,null);
        Assert.assertEquals(ApiEnum.RSLT_CDE_000000.getCode(),response.getMessageCode());
        Assert.assertNotNull(response.getData());
    }

    @Test
    public void updateRole() {
        AuthDetailDTO authDetailDTO = new AuthDetailDTO();
        authDetailDTO.setAuthId(1L);
        authDetailDTO.setAuthName("单测权限");
        authDetailDTO.setUpdateUsr("test");
        JsonResponse response = sysAuthService.updateRole(authDetailDTO,null);
        Assert.assertEquals(ApiEnum.RSLT_CDE_000000.getCode(),response.getMessageCode());
        Assert.assertNotNull(response.getData());
    }

    @Test
    public void changeStatus() {
        JsonResponse response = sysAuthService.changeStatus(1L,CommonEnum.STAT_ACTIVE.getCode(),"test");
        Assert.assertEquals(ApiEnum.RSLT_CDE_000000.getCode(),response.getMessageCode());
        Assert.assertNotNull(response.getData());
    }
}