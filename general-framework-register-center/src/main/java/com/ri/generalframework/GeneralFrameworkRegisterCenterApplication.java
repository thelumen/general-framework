package com.ri.generalframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GeneralFrameworkRegisterCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralFrameworkRegisterCenterApplication.class, args);
    }

}
