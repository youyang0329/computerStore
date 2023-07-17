package com.example.computerstore.service.impl;

import com.example.computerstore.mapper.UserMapper;
import com.example.computerstore.pojo.User;
import com.example.computerstore.service.IUserService;
import com.example.computerstore.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/** 用户模块业务层的实现类 */
@Service
public class IUserServiceImpl implements IUserService {
    //调用mapper层的方法，再把对象传递下去，因此自动装配mapper
    @Autowired(required = false)
    private UserMapper userMapper;

    /**
     * @param user 用户数据对象
     */

    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        if(username == null || username.equals("")){
            throw new InputNullException("用户名输入为空");
        }
        //调用findByUsername(username)判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        //如果结果集部位null则抛出被占用的异常
        if(result != null){
            throw new UsernameDuplicatedException("用户名被占用!");
        }

        //使用盐值+UUID对密码进行加密
        String oldPassword = user.getPassword();
        //获取盐值（随机生成）
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值的记录
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密后的密码重写补全设置到User对象中
        user.setPassword(md5Password);

        //补全数据：is_delete设置为0
        user.setIsDelete(0);
        //四个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        //执行注册业务功能的实现
        Integer rows = userMapper.insert(user);
        //受影响行数不对的话就抛出插入异常
        if(rows != 1){
            throw new InsertException("用户注册过程中产生了未知异常");
        }

    }

    @Override
    public User login(String username, String password) {
        //根据用户名称来查询用户的数据是否存在，如果不在则抛出异常
        User result = userMapper.findByUsername(username);
        if(result == null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //判断is_delete字段的值是否为1表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        //检测用户密码（加盐）是否匹配
        //检测用户的密码是否匹配
        //1.先获取到数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        //2.和用户的传递过来的密码进行比较
        //2.1先获取盐值:上一次在注册时所自动生成的盐值
        String salt = result.getSalt();
        // 2.2将用户的密码按照相同的md5算法的规则进行加密
        String newMd5Password = getMD5Password(password,salt);
        //3．将密码进行比较
        if (!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }


        //新建一个User对象，提升系统的性能，使得每一层之间的数据体量在传输的时候变小了，响应速度也变快
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPassword(result.getPassword());
        user.setAvatar(result.getAvatar());

        return user;
    }

    /** 定义一个md5算法的加密处理 */
    private String getMD5Password(String password,String salt){
        //md5加密算法方法的调用(进行三次加密)
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        //返回加密后的密码
        return password;
    }

    public void changePassword(Integer uid,String username,String insertOldPassword,String insertNewPassword){
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //将页面中输入的旧密码与数据库中密码进行比较，当输入的旧密码确实是正确的时候才能进行设置
        String oldMd5Password = getMD5Password(insertOldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中：将新的密码进行加密再更新
        String newMd5Password = getMD5Password(insertNewPassword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if(rows != 1){
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        //检测给的uid数据存不存在
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //这个result如果存在，那么就是用来回显到前端页面
        //但是建议先创建个空的user对象，然后把数据放到这个对象中，再去使用
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    /**
     * user对象中的数据phone,email,gender，手动再将uid,username封装到user对象
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        //检测给的uid数据存不存在，因为可能会出现悬停了很久都没有操作
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        //user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if(rows != 1){
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前用户数据是否存在
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(rows!=1){
            throw new UpdateException("更新用户头像产生未知的异常");
        }
    }
}
