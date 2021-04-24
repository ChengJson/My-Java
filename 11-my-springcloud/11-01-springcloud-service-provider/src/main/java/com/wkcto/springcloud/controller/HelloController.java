package com.wkcto.springcloud.controller;

import com.wkcto.springcloud.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/service/hello")
    public String hello(){
        return "hello1";
    }

    /**
     * 返回实体类
     * @return
     */
    @RequestMapping("/service/getuser")
    public User getuser(){
        User user = new User();
        user.setAge(123);
        user.setUsername("张三");
        return user;
    }

    /**
     * 接收请求参数
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/service/getuserByid")
    public User getuserByid(@RequestParam("id")Integer id, @RequestParam("name")String name){
        User user = new User();
        user.setAge(id);
        user.setUsername(name);
        return user;
    }


    /**
     * 接收请求参数
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/service/post",method = RequestMethod.POST)
    public User post(@RequestParam("id")Integer id, @RequestParam("name")String name){
        User user = new User();
        user.setAge(id);
        user.setUsername(name);
        return user;
    }


    /**
     * 接收请求参数
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/service/put",method = RequestMethod.PUT)
    public User put(@RequestParam("id")Integer id, @RequestParam("name")String name){
        User user = new User();
        user.setAge(id);
        user.setUsername(name);
        return user;
    }


}
