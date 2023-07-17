package com.example.computerstore.controller;


import com.example.computerstore.controller.ex.*;
import com.example.computerstore.pojo.User;
import com.example.computerstore.service.IUserService;
import com.example.computerstore.service.ex.InsertException;
import com.example.computerstore.service.ex.UsernameDuplicatedException;
import com.example.computerstore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//给每个方法加上@ResponseBody，用来响应结果以json为格式进行数据的响应给到前端
@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
    //依赖业务层
    @Autowired
    private IUserService iUserService;

//    @RequestMapping("/reg")
//    public JsonResult<Void> reg(User user){
//        //创建响应结果对象
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            iUserService.reg(user);
//            result.setState(200);
//            result.setMessage("注册成功");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        }catch (InsertException e){
//            result.setState(5000);
//            result.setMessage("注册时发生未知的异常");
//        }
//        return result;
//    }

    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){

        iUserService.reg(user);
        return new JsonResult<>(OK);

    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = iUserService.login(username,password);

        //向session对象中完成数据的绑定（该session是全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        //获取session中绑定的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<>(OK,data);

    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword,HttpSession session){

        //获取session中绑定的数据
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        iUserService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        //只有登录了session中才有信息，也才能获取uid
        User data = iUserService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象有四部分的数据：username，phone，email，gender
        //表单中的同名字段会自动注入到user对象中
        //uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        iUserService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }


    /** 设置上传文件的最大值 */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 限制上传文件类型 */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    /** 在静态代码块来给它初始化即可 */
    static{
        /** jpg要写成jpeg */
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){
        //1.判断文件是否为null
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        //2.判断文件大小是否符合要求
        if(file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出大小限制");
        }
        //3.判断文件类型是否在限制范围内
        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //4.上传的文件路径：.../upload/文件.png
        //  getServletContext得到上下文路径
        String parent = session.getServletContext().getRealPath("upload");
        //5.new一个File对象指向这个路径，即动态创建，然后检查File是否存在
        File dir = new File(parent);
        //检测目录是否存在
        if(!dir.exists()){
            //目录不存在就创建，目录名为填入getRealPath的参数
            dir.mkdirs();
        }
        //6.获取到文件名，使用UUID工具来生成一个新名字，防止重名
        String originalFilename = file.getOriginalFilename();
        //  获取文件后缀
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //  动态拼接，生成新名字
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //7.创建一个新的File对象，填入参数：指定目录，创建文件名
        File dest = new File(dir,filename); //此时是一个空文件
        //8.将file中的数据写到这个空文件中
        try{
            //无需io流了，已经提供了一个转换的方法
            file.transferTo(dest);
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        } catch (IOException e){
            throw new FileUploadIOException("文件读写异常");
        }
        //9.获取到uid以及username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //10.通过字符串拼接来返回头像的路径，只需要写相对路径即可
        String avatar = "/upload/" + filename;
        iUserService.changeAvatar(uid,avatar,username);
        //11.返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK,avatar);
    }
}
