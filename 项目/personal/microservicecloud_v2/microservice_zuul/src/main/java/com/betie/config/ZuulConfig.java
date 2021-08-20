package com.betie.config;

import com.betie.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {
    @Bean
    public TokenFilter getTokenFilter(){
        return new TokenFilter();
    }
}
