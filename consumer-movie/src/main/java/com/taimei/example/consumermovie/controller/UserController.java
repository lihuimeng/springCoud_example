package com.taimei.example.consumermovie.controller;

import com.alibaba.fastjson.JSON;
import com.taimei.example.consumermovie.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUser")
    public String getUser() {

        User user = new User();
        user.setId(123456);
        user.setName("用户测试 ");
        return JSON.toJSONString(user);
    }

    @RequestMapping("/getOrder")
    @ResponseBody
    public String getOrder() {
        System.out.println("**********请求到达");

        List<String> list = new ArrayList<String>();
        list.add("测试1");
        list.add("测试2");
        list.add("测试3");
        list.add("测试4");
        list.add("测试5");

        String forObject = null;
        try {
            forObject = restTemplate.getForObject("http://order/order/getOrder?exceptionTest={2}", String.class,"没有异常！！");
        } catch (RestClientException e) {
            System.out.println("以尝捕捉，进行重试");
            Map<String, String> map = new HashMap<String, String>();
            map.put("exceptionTest", "发生异常，重新发送");

            forObject = restTemplate.getForObject("http://order/order/getOrder?exceptionTest={exceptionTest}", String.class, map);
        }


        return forObject;
    }
}
