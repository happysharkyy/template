package com.ncu.building;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.ncu.building.mapper")
@SpringBootApplication
public class ncuApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ncuApplication.class, args);
    }
}

