package com.example.computerstore.service;


import com.example.computerstore.pojo.User;
import com.example.computerstore.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//表=表示当前注解的类是一个测试类，不会随项目一同打包
@SpringBootTest
public class UserServiceTests {

    //自动装配到容器中，标红的话要在mapper接口加注解@Mapper
    @Autowired(required = false)
    private IUserService iUserService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("youyang123456");
            user.setPassword("123456");
            iUserService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = iUserService.login("youyang", "123123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        iUserService.changePassword(15,"管理员","123456","654321");
    }

    @Test
    public void getByUid(){
        System.err.println(iUserService.getByUid(15));
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("17844440000");
        user.setEmail("yuan@qq.com");
        user.setGender(0);
        iUserService.changeInfo(15,"管理员",user);
    }

    @Test
    public void changeAvatar(){
        iUserService.changeAvatar(16,"/upload/test.png","小明");

    }

}
