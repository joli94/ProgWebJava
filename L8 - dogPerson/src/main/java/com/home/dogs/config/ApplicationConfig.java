package com.home.dogs.config;

import com.home.dogs.mapper.DogMapper;
import com.home.dogs.mapper.DogMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DogMapper dogMapper(){
        return new DogMapperImpl();
    }
}
