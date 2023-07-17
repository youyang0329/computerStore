package com.example.computerstore.mapper;


import com.example.computerstore.pojo.Order;
import com.example.computerstore.pojo.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class OrderMapperTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Test
    public void  insertOrder(){
        Order order = new Order();
        order.setUid(22);
        order.setRecvName("小帅");
        order.setRecvPhone("17800000000");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(10000002);
        orderItem.setTitle("广博(GuangBo)皮面日程本子 计划记事本效率手册米色FB60322广博(GuangBo)皮面日程本子 计划记事本效率手册米色FB60322");;
        orderMapper.insertOrderItem(orderItem);
    }


}
