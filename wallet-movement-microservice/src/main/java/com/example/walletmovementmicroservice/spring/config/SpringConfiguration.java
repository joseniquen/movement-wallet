package com.example.walletmovementmicroservice.spring.config;

import com.example.walletmovementmicroservice.aplication.model.MovementRepository;
import com.example.walletmovementmicroservice.aplication.model.WalletRepository;
import com.example.walletmovementmicroservice.infraestructure.respository.MovementCrudRepository;
import com.example.walletmovementmicroservice.infraestructure.respository.WalletCrudRespository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public MovementRepository movementRepository() {
        return new MovementCrudRepository();
    }

    @Bean
    public WalletRepository walletRepository() {
        return new WalletCrudRespository();
    }
}
