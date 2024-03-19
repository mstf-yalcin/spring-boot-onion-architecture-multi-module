package com.onionarchitecture.infrastructure.config;

import com.onionarchitecture.application.services.ProductService;
import com.onionarchitecture.application.services.imp.ProductServiceImp;
import com.onionarchitecture.infrastructure.service.cacheservice.ProductServiceWithCacheImp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class FactoryConfig {

    @Bean
    @Qualifier
    public ProductService productService(ProductServiceImp productServiceImp, RedisTemplate<String,Object> redisTemplate)
    {
        return new ProductServiceWithCacheImp(productServiceImp,redisTemplate);
    }
}
