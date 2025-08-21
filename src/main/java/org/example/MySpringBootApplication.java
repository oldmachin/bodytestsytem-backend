package org.example; // 这是你的主应用程序类的包名

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // 导入这个注解

@SpringBootApplication
@ComponentScan(basePackages = {"org.example", "org.example.config"})
public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}