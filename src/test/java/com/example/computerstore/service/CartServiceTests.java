package com.example.computerstore.service;


import com.example.computerstore.pojo.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class CartServiceTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private ICartService cartService;

    @Test
    public void addNewAddress(){
        //已存在的就是只增加数量
        cartService.addToCart(16,10000003,10,"小明");
    }

}
