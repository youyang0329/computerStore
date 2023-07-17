package com.example.computerstore.service;

import com.example.computerstore.pojo.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 收货地址业务层接口
 */

public interface IAddressService {

    //这里的username不一定是用户名，而也可以是修改人的名称
    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户的id
     * @param username 表示修改的执行人
     */
    void setDefault(Integer aid, Integer uid,String username);


    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 用户名
     */
    void delete(Integer aid,Integer uid,String username);


    /**
     * 更新
     * @param aid
     * @param name 收货人姓名
     */
    void updateByAid(Integer aid,Integer uid,String name,String provinceName,
                     String cityName,String areaName,String zip,
                     String address,String phone,String tel,String tag,
                     String modifiedUser,Date modifiedTime
    );

    Address getByAid(Integer aid, Integer uid);
}
