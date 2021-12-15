package com.example.walletmovementmicroservice.aplication;

import com.example.walletmovementmicroservice.aplication.impl.ResponseService;
import com.example.walletmovementmicroservice.domain.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletOperations {
    public Flux<Wallet> list();

    public Flux<Wallet> listByDocument(String document);

    public Mono<Wallet> get(String id);

    public Mono<ResponseService> create(Wallet wallet);

    public Mono<Wallet> update(String id, Wallet wallet);

    public void delete(String id);
}
