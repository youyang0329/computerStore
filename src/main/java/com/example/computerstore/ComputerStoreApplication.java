package com.example.computerstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//MapperScan注解制定当前项目中的Mapper接口路径的位置，项目启动时候会自动扫包来加载所有的接口
@MapperScan("com.example.computerstore.mapper")
public class ComputerStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputerStoreApplication.class, args);
    }

}
