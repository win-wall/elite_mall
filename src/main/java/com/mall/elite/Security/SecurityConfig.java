package com.mall.elite.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        //! Spring Security will help us to encode the password
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    //* this "UserDetailsService provide Spring security a user
    public UserDetailsService userDetailsService(){
        //! remember to delete this. it is just testing JWT
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("tuong")
                        .password("tuong123")
                        .roles("USER")
                        .build()
        );
        return manager;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll() //! Allow all people to access this
                .anyRequest().authenticated()//! Other request need to autheticated to access
                .and()
                .formLogin() //Cho phép người dùng xác thực bằng form login
                .defaultSuccessUrl("/Hello")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
