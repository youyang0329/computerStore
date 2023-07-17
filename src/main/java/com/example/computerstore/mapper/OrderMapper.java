package com.example.computerstore.mapper;


import com.example.computerstore.pojo.Order;
import com.example.computerstore.pojo.OrderItem;

/** 处理订单及订单商品数据的持久层接口 */
public interface OrderMapper {
    /* 插入订单数据*/
    Integer insertOrder(Order order);

    /*插入订单商品数据*/
    Integer insertOrderItem(OrderItem orderItem);
}
