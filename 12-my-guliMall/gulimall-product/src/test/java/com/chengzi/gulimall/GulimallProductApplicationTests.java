package com.chengzi.gulimall;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.gulimall.product.GulimallProductApplication;
import com.chengzi.gulimall.product.entity.BrandEntity;
import com.chengzi.gulimall.product.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = GulimallProductApplication .class)
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;



    @Test
    public void testFindPath(){
        //Long[] catelogPath = categoryService.findCatelogPath(225L);
        //log.info("完整路径：{}",Arrays.asList(catelogPath));
    }


    @Test
    public void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("华为");


        brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功....");

//        brandService.updateById(brandEntity);

//
//        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
//        list.forEach((item) -> {
//            System.out.println(item);
//        });

    }

}
