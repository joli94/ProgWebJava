package com.proiect.chemdb.config;

import com.proiect.chemdb.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public BuySubstanceMapper buySubstanceMapper() {return new BuySubstanceMapperImpl(); }

    @Bean
    public ConsumedSubstanceMapper consumedSubstanceMapper() { return new ConsumedSubstanceMapperImpl(); }

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public SubstanceMapper substanceMapper() {
        return new SubstanceMapperImpl();
    }


}
