package com.bootcamp.movementwallet.application;

import com.bootcamp.movementwallet.domain.movementWallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementWalletOperations {
    public Mono<movementWallet> get(String celularEmisor);

    public Flux<movementWallet> listAll();

    public Flux<movementWallet> listWalletCelularDestino(String celularDestino);

    public Mono<movementWallet> create(movementWallet mw);

    public Mono<movementWallet> update(String celularEmisor, movementWallet mw);

    public void delete(String celularEmisor);
}
