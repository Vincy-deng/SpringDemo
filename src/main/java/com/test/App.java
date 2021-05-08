package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
//用于扫描Dao层@Repository
@EnableJpaRepositories(basePackages = "com.test.dao")
//用于扫描JPA实体类 @Entity
@EntityScan(basePackages = "com.test.vo")
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(App.class,args);
        System.out.printf("该服务器的名称为："+context.getEnvironment().getProperty("spring.application.name"));
        System.out.println("该服务器启动的端口："+context.getEnvironment().getProperty("server.port"));
    }
}

