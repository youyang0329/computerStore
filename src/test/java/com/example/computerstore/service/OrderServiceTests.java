package com.example.computerstore.service;


import com.example.computerstore.pojo.Address;
import com.example.computerstore.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class OrderServiceTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private IOrderService orderService;

    @Test
    public void create(){
        Integer[] cids = new Integer[]{2, 3, 4, 5};
        Order order = orderService.create(10, cids, 16, "youyang999");
        System.err.println(order);
    }

}
