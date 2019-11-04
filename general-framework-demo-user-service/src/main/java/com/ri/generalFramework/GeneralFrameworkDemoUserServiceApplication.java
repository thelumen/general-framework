package com.ri.generalFramework;

import com.ri.generalFramework.filter.SecurityFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GeneralFrameworkDemoUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralFrameworkDemoUserServiceApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setName("securityFilter");
        SecurityFilter securityFilter = new SecurityFilter();
        registrationBean.setFilter(securityFilter);
        registrationBean.setOrder(1);
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registrationBean.setUrlPatterns(urlList);
        return registrationBean;
    }
}
