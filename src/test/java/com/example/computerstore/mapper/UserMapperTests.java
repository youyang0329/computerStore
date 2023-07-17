package com.example.computerstore.mapper;

import com.example.computerstore.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class UserMapperTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("asdf");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(13,"321","管理员",new Date());

    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(13));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(15);
        user.setPhone("15511110000");
        user.setEmail("test002@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(16,"/upload/avatar.png","管理员",new Date());
    }
}
