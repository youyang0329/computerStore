package com.example.computerstore.service.impl;

import com.example.computerstore.mapper.AddressMapper;
import com.example.computerstore.mapper.DistrictMapper;
import com.example.computerstore.pojo.Address;
import com.example.computerstore.pojo.District;
import com.example.computerstore.service.IAddressService;
import com.example.computerstore.service.IDistrictService;
import com.example.computerstore.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 新增收货地址的实现类
 */
@Service
public class IAddressServiceImpl implements IAddressService {

    //要判断mapper层的方法调用，因此自动注入mapper
    @Autowired
    private AddressMapper addressMapper;

    //在添加用户的收货地址的业务层依赖于districtService的业务层接口
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收货地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址超限!");
        }

        //对address对象中的数据进行补全，省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        //便于列表展示，要uid
        address.setUid(uid);
        //控制在插入收货地址是不是默认
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        //补全四项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户数据产生未知异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        //先找是否存在这个收货地址
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        //检测当前获取到的收货地址数据的归属是否属于当前用户
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        //先将所有的收货地址设置非默认
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
        //将用户选中的某条地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        //先找需要的地址是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        //检测当前获取到的收货地址数据的归属是否属于当前用户
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据产生未知的异常");
        }
        //判断删除完后是否全删完了，如果是，则无需后面的操作
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }
        //判断删除的是否是默认地址
        if (result.getIsDefault() == 0) {
            return;
        }

        //自动设置默认地址，将这条数据中的is_default字符的值设置1
        Address address = addressMapper.findLastModified(uid);
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public void updateByAid(Integer aid, Integer uid, String name,
                            String provinceName, String cityName, String areaName,
                            String zip, String address, String phone,
                            String tel, String tag, String modifiedUser,
                            Date modifiedTime
    ) {
        //先找需要的地址是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        //检测当前获取到的收货地址数据的归属是否属于当前用户
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        String provinceCode = districtService.findCodeByName(provinceName);
        String cityCode = districtService.findCodeByName(cityName);
        //省市名都是唯一的，区名可能会重复，因此我们要使用getByParent来判断
        List<District> areaCodeList = districtService.getByParent(cityCode);
        String areaCode = null;
        for (District a : areaCodeList) {
            if (a.getName().equals(areaName)) {
                areaCode = a.getCode();
                break;
            }
        }
        if (areaCode == null) {
            throw new UpdateException("修改数据时产生未知的异常");
        }
        Integer rows = addressMapper.updateByAid(aid, name, provinceName, provinceCode, cityName, cityCode, areaName, areaCode
                , zip, address, phone, tel, tag, modifiedUser, modifiedTime);
        if (rows != 1) {
            throw new UpdateException("修改数据时产生未知的异常");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据方法");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }

}
