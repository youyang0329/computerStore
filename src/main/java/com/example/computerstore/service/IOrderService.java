package com.example.computerstore.service;

import com.example.computerstore.pojo.Order;

/** 处理订单和订单数据的业务层接口 */
public interface IOrderService {
    /*创建订单*/
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
