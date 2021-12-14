package com.bootcamp.movementwallet.application.impl;

import com.bootcamp.movementwallet.application.MovementWalletOperations;
import com.bootcamp.movementwallet.application.model.MovementWalletRepository;
import com.bootcamp.movementwallet.domain.movementWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovementWalletOperationsImpl implements MovementWalletOperations {

    private final MovementWalletRepository movementWalletRepository;
    @Override
    public Mono<movementWallet> get(String celularEmisor) {
        return movementWalletRepository.get(celularEmisor);
    }

    @Override
    public Flux<movementWallet> listAll() {
        return movementWalletRepository.listAll();
    }

    @Override
    public Flux<movementWallet> listWalletCelularDestino(String celularDestino) {
        return movementWalletRepository.listWalletCelularDestino(celularDestino);
    }

    @Override
    public Mono<movementWallet> create(movementWallet mw) {
        return movementWalletRepository.create(mw);
    }

    @Override
    public Mono<movementWallet> update(String celularEmisor, movementWallet mw) {
        return movementWalletRepository.update(celularEmisor,mw);
    }

    @Override
    public void delete(String celularEmisor) {
        movementWalletRepository.delete(celularEmisor);
    }
}
