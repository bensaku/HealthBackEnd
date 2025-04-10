package com.hfut.mihealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hfut.mihealth.mapper")
public class MiHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiHealthApplication.class, args);
    }

}
