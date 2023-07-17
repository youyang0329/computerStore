package com.example.computerstore.controller;

import com.example.computerstore.pojo.Address;
import com.example.computerstore.service.IAddressService;
import com.example.computerstore.service.ex.UpdateException;
import com.example.computerstore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<Address>> getByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(OK,data);
    }

    //RestFul风格的请求编写
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
        addressService.setDefault(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid")Integer aid,HttpSession session){
        addressService.delete(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("{aid}/update")
    public JsonResult<Void> update(@PathVariable("aid")Integer aid,HttpSession session){
        JsonResult<List<Address>> result = getByUid(session);
        List<Address> data = result.getData();
        Address address = null;
        for(Address a : data){
            if(a.getAid().equals(aid)){
                address = a;
                break;
            }
        }
        if(address == null){
            throw new UpdateException("修改时产生未知异常");
        }
        addressService.updateByAid(aid,address.getUid(),address.getName(),address.getProvinceName(),address.getCityName(),
                address.getAreaName(),address.getZip(),address.getAddress(),address.getPhone(),address.getTel(),
                address.getTag(),address.getModifiedUser(),new Date());
        return new JsonResult<>(OK);
    }

}
