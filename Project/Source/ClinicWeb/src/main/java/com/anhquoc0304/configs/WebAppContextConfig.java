/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.configs;

import com.anhquoc0304.formatters.CategoryFormatters;
import com.anhquoc0304.formatters.RoomFormatters;
import com.anhquoc0304.formatters.SpecializationFormatters;
import com.anhquoc0304.formatters.UnitMedicineFormatters;
import com.anhquoc0304.formatters.UserFormatters;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.anhquoc0304.controllers",
    "com.anhquoc0304.service",
    "com.anhquoc0304.repository",
    "com.anhquoc0304.components",
    "com.anhquoc0304.restapis"
})
public class WebAppContextConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/resources/css/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("/resources/images/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/resources/scripts/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new CategoryFormatters());
        registry.addFormatter(new UnitMedicineFormatters());
        registry.addFormatter(new UserFormatters());
        registry.addFormatter(new RoomFormatters());
        registry.addFormatter(new SpecializationFormatters());
    }
    
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvevyvqyt",
                "api_key", "595363456269972",
                "api_secret", "qtQWMeNL6dcV1eM9ZLXEjJAMims",
                "secure", true));
        return cloudinary;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean
                = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resource
                = new ResourceBundleMessageSource();
//        resource.setBasename("validation");
        resource.setBasename("validation");
        return resource;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

}
