package com.example.walletmovementmicroservice.aplication.model;

import com.example.walletmovementmicroservice.domain.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletRepository {

    public Flux<Wallet> list();

    public Flux<Wallet> listByDocument(String document);

    public Mono<Wallet> get(String id);

    public Mono<Wallet> create(Wallet d);

    public Mono<Wallet> update(String id, Wallet d);

    public void delete(String id);
}
