package com.ri.generalFramework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/user/test/**").hasRole("user")
                .antMatchers("/api/user/testAdmin/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .and()
//                .httpBasic()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("admin1") // 管理员，同事具有 ADMIN,USER权限，可以访问所有资源
                .password("admin1")
                .roles("admin", "user")
                .and()
                .withUser("user1")
                .password("user1") // 普通用户，只能访问 /product/**
                .roles("user");
    }

    class MyPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return s.equals(charSequence.toString());
        }
    }
}
