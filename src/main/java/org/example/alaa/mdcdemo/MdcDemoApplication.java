package org.example.alaa.mdcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MdcDemoApplication {

    public static void main(String[] args) {
        System.out.println("Test");
        SpringApplication.run(MdcDemoApplication.class, args);
    }

}
