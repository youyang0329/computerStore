package com.example.computerstore.service;



import com.example.computerstore.vo.CartVO;

import java.util.List;

/* 处理商品数据的业务层接口 */
public interface ICartService {

    /*将商品添加到购物车*/
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 更新用户的购物车数据数量
     * @param cid
     * @param uid
     * @param username
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /*将购物车中某商品的数量减1*/
    Integer reduceNum(Integer cid, Integer uid, String username);

    /*查询某用户的购物车数据*/
    List<CartVO> getVOByUid(Integer uid);

    /*依照购物车的归属来根据若干个购物车数据id查询详情的列表*/
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
