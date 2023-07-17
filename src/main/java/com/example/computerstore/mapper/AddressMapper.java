package com.example.computerstore.mapper;

import com.example.computerstore.pojo.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {
    /*插入收货地址数据*/
    Integer insert(Address address);

    /*统计某用户的收货地址数据的数量*/
    Integer countByUid(Integer uid);

    /*查询某用户的收货地址列表数据*/
    List<Address> findByUid(Integer uid);

    /*将某用户的所有收货地址设置为非默认地址*/
    Integer updateNonDefaultByUid(Integer uid);

    /* 将指定的收货地址设置为默认地址*/
    Integer updateDefaultByAid(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /* 根据收货地址aid值，查询收货地址详情*/
    Address findByAid(Integer aid);

    /* 根据收货地址aid删除数据*/
    Integer deleteByAid(Integer aid);

    /* 根据uid查询某用户最后修改的收货地址*/
    Address findLastModified(Integer uid);

    /**
     * 根据aid来修改收货地址
     * @param aid
     * @return 受影响行
     */
    Integer updateByAid(@Param("aid")Integer aid,
                        @Param("name")String name,
                        @Param("provinceName")String provinceName,
                        @Param("provinceCode")String provinceCode,
                        @Param("cityName")String cityName,
                        @Param("cityCode")String cityCode,
                        @Param("areaName")String areaName,
                        @Param("areaCode")String areaCode,
                        @Param("zip")String zip,
                        @Param("address")String address,
                        @Param("phone")String phone,
                        @Param("tel")String tel,
                        @Param("tag")String tag,
                        @Param("modifiedUser") String modifiedUser,
                        @Param("modifiedTime") Date modifiedTime
    );


}
