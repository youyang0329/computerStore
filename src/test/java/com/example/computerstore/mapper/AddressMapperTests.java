package com.example.computerstore.mapper;

import com.example.computerstore.pojo.Address;
import com.example.computerstore.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class AddressMapperTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(23);
        address.setPhone("17858802974");
        address.setName("女朋友");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(23);
        System.out.println(count);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(23);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.err.println(addressMapper.findByAid(8));
    }

    @Test
    public void updateNonDefault(){
        addressMapper.updateNonDefaultByUid(16);
    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(8,"小明",new Date());
    }

    @Test
    public void deleteByAid(){
        addressMapper.deleteByAid(2);
    }

    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(16));
    }

    @Test
    public void updateByAid(){
        System.out.println(
                addressMapper.updateByAid(10,"关海山",
                        "吉林省","220000",
                        "吉林市","220200",
                        "龙潭区","220203",
                        "12345","广州交通大学",
                        "15900145660","060-88888",
                        "学校","管理员",new Date()));
    }

}
