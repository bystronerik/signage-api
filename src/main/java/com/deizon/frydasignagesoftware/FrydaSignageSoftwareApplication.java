package com.deizon.frydasignagesoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.deizon")
@SpringBootApplication
public class FrydaSignageSoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrydaSignageSoftwareApplication.class, args);
    }
}
