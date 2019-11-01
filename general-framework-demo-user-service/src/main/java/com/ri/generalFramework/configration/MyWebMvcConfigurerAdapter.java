package com.ri.generalFramework.configration;

import com.ri.generalFramework.GeneralFrameworkDemoUserServiceApplication;
import com.ri.generalFramework.interceptor.ActionInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackageClasses = GeneralFrameworkDemoUserServiceApplication.class, useDefaultFilters = true)
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {
    /***
     * 添加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //Action的注册
        ActionInterceptor actionInterceptor = new ActionInterceptor();
        registry.addInterceptor(actionInterceptor);

    }

}
