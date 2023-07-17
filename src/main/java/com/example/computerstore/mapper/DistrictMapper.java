package com.example.computerstore.mapper;

import com.example.computerstore.pojo.District;


import java.util.List;

public interface DistrictMapper {
    List<District> findByParent(String parent);
    String findNameByCode(String code);
    //根据名字获取编号
    String findCodeByName(String name);
}
