package com.example.computerstore.service.impl;

import com.example.computerstore.mapper.DistrictMapper;
import com.example.computerstore.pojo.District;
import com.example.computerstore.service.IDistrictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 处理省/市/区数据的业务层实现类
@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }

    @Override
    public String findCodeByName(String name) {
        return districtMapper.findCodeByName(name);
    }


}