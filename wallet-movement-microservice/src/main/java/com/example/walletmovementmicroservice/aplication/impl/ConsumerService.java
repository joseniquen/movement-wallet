package com.example.walletmovementmicroservice.aplication.impl;

import com.example.walletmovementmicroservice.aplication.WalletOperations;
import com.example.walletmovementmicroservice.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final WalletOperations operations;

    @KafkaListener
    (topics = "topic-wallet", containerFactory = "walletKafkaListenerContainerFactory")
    public void createWallet(Wallet wallet) {
        Mono<ResponseService> rs = operations.create(wallet);
        rs.subscribe(w -> {
            System.out.println(w.getMessage());
        });

    }
}
