package com.example.computerstore.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//作为实体类的基础类
@Data
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;

}
