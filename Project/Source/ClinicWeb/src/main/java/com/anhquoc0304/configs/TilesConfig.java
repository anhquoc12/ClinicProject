/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

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
    "com.anhquoc0304.repository"
})
public class TilesConfig {
    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(-2);
        return viewResolver;
    }
    
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tile = new TilesConfigurer();
        tile.setDefinitions("/WEB-INF/tiles.xml");
        tile.setCheckRefresh(true);
        return tile;
    }
    
   
}
