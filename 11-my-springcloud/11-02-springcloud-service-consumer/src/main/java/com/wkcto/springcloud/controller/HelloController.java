package com.wkcto.springcloud.controller;
import com.wkcto.springcloud.model.User;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;


    /**
     *
     * @return
     */
    @RequestMapping("/web/hello")
    public String hello(){
        //return restTemplate.getForEntity("http://localhost:8080/service/hello",String.class).getBody();

        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hello",String.class).getBody();
    }


    /**
     * RestTemplate的get请求使用
     * @return
     */
    @RequestMapping("/web/hello1")
    public String get(){

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hello", String.class);
        String body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println(body);
        System.out.println(statusCode);
        System.out.println(headers);
        System.out.println(statusCodeValue);
        return body;

    }


    /**
     * 传递对象
     * @return
     */
    @RequestMapping("/web/getuser")
    public String getuser(){

        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/getuser", User.class);
        User body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println(body);
        System.out.println(statusCode);
        System.out.println(headers);
        System.out.println(statusCodeValue);
        return body.getUsername();

    }


    /**
     * 传递请求参数
     * @return
     */
    @RequestMapping("/web/getuserByid")
    public String getuserByid(){

        String[] strings = {"10","zhangsan"};
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/getuserByid?id={0}&name={1}", User.class,strings);
        User body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println(body);
        System.out.println(statusCode);
        System.out.println(headers);
        System.out.println(statusCodeValue);
        return body.getUsername();

    }

    /**
     * 传递请求参数map
     * @return
     */
    @RequestMapping("/web/getuserByMap")
    public String getuserByMap(){

        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id",1);
        stringStringHashMap.put("name","zhangsanaaa");

        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/getuserByid?id={id}&name={name}", User.class,stringStringHashMap);
        User body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println(body);
        System.out.println(statusCode);
        System.out.println(headers);
        System.out.println(statusCodeValue);
        return body.getUsername();

    }

    /**
     * getforObject  get：查询操作
     * @return
     */
    @RequestMapping("/web/getforObject")
    public String getforObject(){
        /**也可以和getForEntity一样传递数组**/
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id",1);
        stringStringHashMap.put("name","zhangsanaaa");

        User body = restTemplate.getForObject("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/getuserByid?id={id}&name={name}", User.class,stringStringHashMap);
        System.out.println(body);
        return body.getUsername();

    }


    /**
     * restTemplate的post请求:添加操作
     * @return
     */
    @RequestMapping("/web/post")
    public String post(){
        /**也可以和getForEntity一样传递数组**/

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("id",1);
        map.add("name","zhangsanaaa");
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/post", map, User.class);
        User user = restTemplate.postForObject("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/post", map, User.class);
        System.out.println(userResponseEntity.getBody());
        return userResponseEntity.getBody().getUsername();

    }


    /**
     * restTemplate的put请求:修改操作
     * @return
     */
    @RequestMapping("/web/put")
    public String put(){

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("id",1);
        map.add("name","zhangsanaaa");
        restTemplate.put("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/put", map);
        return "success";
    }


    /**
     * restTemplate的delete请求:修改操作
     * @return
     */
    @RequestMapping("/web/delete")
    public String delete(){

        String[] strings = {"10","zhangsan"};
        restTemplate.delete("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/delete?id={0}&name={1}",strings);

        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id",1);
        stringStringHashMap.put("name","zhangsanaaa");
        restTemplate.delete("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/delete?id={id}&name={name}", stringStringHashMap);
        return "success";
    }
}
