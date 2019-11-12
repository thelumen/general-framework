package com.ri.generalFramework;

import com.ri.generalFramework.filter.TraceFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableOAuth2Sso
public class DemoUserBServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoUserBServiceApplication.class, args);
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
