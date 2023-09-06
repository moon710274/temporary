package com.seb_main_034.SERVER.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class RoleSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/movies/**").hasRole("ADMIN") // "/movies"
                .antMatchers("/movies").hasRole("USER")
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
