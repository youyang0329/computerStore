package com.example.computerstore.util;

import lombok.Data;

import java.io.Serializable;

/**
 * Json格式的数据进行响应
 */

public class JsonResult<E> implements Serializable {
    /** 响应状态码 */
    private Integer state;

    /** 描述信息 */
    private String message;

    /** 对应数据类型 */
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public E getData() {
        return data;
    }
    public void setData(E data) {
        this.data = data;
    }

    public JsonResult(Integer state) {
        this.state = state;
    }
    //创建无参有参构造，get和set
    //增加一个获取异常
    public JsonResult(Throwable e){
        this.message = e.getMessage();
    }
}
