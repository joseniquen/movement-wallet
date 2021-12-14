package com.bootcamp.movementwallet.spring.config;

import com.bootcamp.movementwallet.application.model.MovementWalletRepository;
import com.bootcamp.movementwallet.infraestructure.repository.MovementWalletCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {
    @Bean
    public MovementWalletRepository movementWalletRepository() {
        return new MovementWalletCrudRepository();
    }
}
