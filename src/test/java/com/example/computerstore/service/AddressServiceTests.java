package com.example.computerstore.service;


import com.example.computerstore.pojo.Address;
import com.example.computerstore.pojo.User;
import com.example.computerstore.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class AddressServiceTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("17858802999");
        address.setName("男朋友");
        addressService.addNewAddress(23,"管理员",address);
    }

    @Test
    public void setDefault(){
        addressService.setDefault(7,16,"管理员");
    }

    @Test
    public void delete(){
        addressService.delete(4,16,"管理员");
    }

    @Test
    public void update(){
        addressService.updateByAid(11,16,"杜阳","吉林省","长春市","南关区",
                "789456","长春工业大学","15900145600","060-555","学校","管理员",
                new Date());
    }
}
