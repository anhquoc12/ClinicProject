/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.configs;

import com.anhquoc0304.handlers.LoginHandler;
import com.anhquoc0304.handlers.LogoutHandler;
import com.anhquoc0304.pojo.User;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.anhquoc0304.service",
    "com.anhquoc0304.repository",
    "com.anhquoc0304.handlers",
    "com.anhquoc0304.controllers"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private LogoutHandler logoutHandler;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CommonsMultipartResolver MultipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setDefaultEncoding("UTF-8");
        return multipart;
    }
    
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvevyvqyt",
                "api_key", "595363456269972",
                "api_secret", "qtQWMeNL6dcV1eM9ZLXEjJAMims",
                "secure", true));
        
        return c;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password");
        
        http.formLogin().successHandler(loginHandler).failureUrl("/login?error");

        
        http.logout().logoutSuccessHandler(logoutHandler);
        
        http.exceptionHandling().accessDeniedPage("/login?accessDenied");
        
        http.authorizeHttpRequests().antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasAuthority(User.ADMIN)
                .antMatchers("/appointment/**").hasAuthority(User.PATIENT)
                .antMatchers("/infoUser/**").hasAnyAuthority(
                User.ADMIN, User.DOCTOR, User.NURSE, User.PATIENT)
                .antMatchers("/schedule/**").hasAnyAuthority(
                User.ADMIN, User.DOCTOR, User.NURSE)
                .antMatchers("/listAppointment/**")
                .hasAnyAuthority(User.PATIENT)
                .antMatchers("/nurse/**")
                .hasAuthority(User.NURSE)
                .antMatchers("/doctor/**")
                .hasAuthority(User.DOCTOR);
        
                
        http.csrf().disable();
        
    }
    
}
