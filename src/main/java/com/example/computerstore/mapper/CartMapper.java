package com.example.computerstore.mapper;

import com.example.computerstore.pojo.Cart;
import com.example.computerstore.vo.CartVO;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart
     * @return 行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车某件商品的数量
     * @param cid 购物车数据id
     * @param num 更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);


    /**
     * 根据用户的id和商品的id来查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 以cart对象存储
     */
    Cart findByUidAndPid(Integer uid,Integer pid);

    Cart findByCid(Integer cid);


    List<CartVO> findVOByUid(Integer uid);

    List<CartVO> findVOByCids(Integer[] cids);


}
