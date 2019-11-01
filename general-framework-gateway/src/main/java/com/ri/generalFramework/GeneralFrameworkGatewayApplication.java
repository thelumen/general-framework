package com.ri.generalFramework;

import com.ri.generalFramework.filterFactory.RequestTimeGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GeneralFrameworkGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralFrameworkGatewayApplication.class, args);
    }

    @Bean
    public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }
}
