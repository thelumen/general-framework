package com.ri.generalFramework;

import com.ri.generalFramework.filter.TraceFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoUserServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean securityFilterRegistrationBean() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setName("traceFilter");
        TraceFilter traceFilter = new TraceFilter();
        registrationBean.setFilter(traceFilter);
        registrationBean.setOrder(1);
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registrationBean.setUrlPatterns(urlList);
        return registrationBean;
    }
}
