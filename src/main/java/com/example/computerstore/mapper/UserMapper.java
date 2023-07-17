package com.example.computerstore.mapper;

//用户模块的持久层接口

import com.example.computerstore.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;


//@Mapper
//不建议在mapper上写这个注解，而是在主启动类上加MapperScanner注解，直接扫包


public interface UserMapper {

    /**
     * 插入用户的数据
     * @param user
     * @return 受影响的行数（增删改都会有受影响行数，通过返回值来判断受影响的行数
     */
    Integer insert(User user);


    /**
     * 根据用户名来查询用户的数据
     * @param username
     * @return 如果找到对应的用户就返回用户数据，否则返回null
     */
    User findByUsername(String username);


    /**
     * 根据用户uid来修改用户密码
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return 受影响行数
     */
    Integer updatePasswordByUid(Integer uid, String password,
                                String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id查询用户数据
     * @param uid
     * @return 找到则返回对象，否则返回null
     */
    User findByUid(Integer uid);


    /**
     * 更新用户的数据信息
     * @param user
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);


    /**
     * 根据用户uid值来修改用户的头像：
     * 解决的问题，当SQL语句的占位符和映射的接口方法参数名不一致的时候，
     * 需要将某个参数强行注入到某个占位符变量上，
     * 可以使用@Param这个注解来标注映射的关系
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return 受影响行数
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);

}
