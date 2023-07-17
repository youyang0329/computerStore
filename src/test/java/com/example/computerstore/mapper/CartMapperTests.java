package com.example.computerstore.mapper;

import com.example.computerstore.pojo.Address;
import com.example.computerstore.pojo.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class CartMapperTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(16);
        cart.setPid(10000001);
        cart.setNum(2);
        cart.setPrice(100L);
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid(){
        cartMapper.updateNumByCid(1,4,"小明",new Date());
    }

    @Test
    public void findByUidAndPid(){
        Cart cart = cartMapper.findByUidAndPid(16, 10000001);
        System.err.println(cart);
    }

    @Test
    public void findVOByUid(){
        System.out.println(cartMapper.findVOByUid(16));
    }

    @Test
    public void findByCid(){
        System.out.println(cartMapper.findByCid(1));
    }



    @Test
    public void findVOByCids(){
        Integer[] cids = {1,2,3,4,30,20,24};
        System.out.println(cartMapper.findVOByCids(cids));
    }





}
