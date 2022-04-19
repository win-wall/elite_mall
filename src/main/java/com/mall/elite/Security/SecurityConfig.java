package com.mall.elite.Security;

import com.mall.elite.Security.jwt.JwtAuthenticationFilter;
import com.mall.elite.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //! Spring Security will help us to encode the password
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailService) //! This line is we provide UserDetail Service cho Spring Security
                .passwordEncoder(passwordEncoder());
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
        http.cors().and().csrf().disable();
        // Set session management to stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Set unauthorized requests exception handler
        http.authorizeRequests()
                .antMatchers("/login", "/logout", "/register").permitAll()
                .antMatchers("/admin/**").permitAll()
                .anyRequest().permitAll();
        //http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);//! Allow all people to access this
                //! Other request need to autheticated to access

        //http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
