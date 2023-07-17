package com.example.computerstore.service;

import com.example.computerstore.pojo.User;

/** 用户模块业务层接口 */
public interface IUserService {
    /**
     * 用户注册方法，业务层无需返回值
     * @param user 用户数据对象
     * @return
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @return 当前品牌的用户数据，如果没有则返回null值
     */
    User login(String username,String password);


    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据用户的id查询用户的数据
     * @param uid
     * @return 用户的数据，接收前端所能动态提交的数据：用户名，电话号码，电子邮箱，性别，使用user来接收便于往下传
     */
    User getByUid(Integer uid);


    /**
     * @param uid 登录后在控制层通过session中获取的uid
     * @param username 登录后在控制层通过session中获取的username
     * @param user 前端所能动态提交的数据：用户名，电话号码，电子邮箱，性别，使用user来接收便于往下传
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 修改用户的头像
     * @param uid 用户id
     * @param avatar 用户头像的路径
     * @param username 用户的名称
     */
    void changeAvatar(Integer uid,String avatar,String username);
}
