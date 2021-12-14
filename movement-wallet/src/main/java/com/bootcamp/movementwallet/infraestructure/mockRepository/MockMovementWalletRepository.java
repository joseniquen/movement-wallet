package com.bootcamp.movementwallet.infraestructure.mockRepository;

import com.bootcamp.movementwallet.application.model.MovementWalletRepository;
import com.bootcamp.movementwallet.domain.movementWallet;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class MockMovementWalletRepository implements MovementWalletRepository {

    @Override
    public Mono<movementWallet> get(String idMovement) {
        movementWallet w = new movementWallet();
        w.setTipoMov("34984545");
        w.setCelularEmisor("123456789");
        w.setCelularDestino("987654321");
        w.setMonto(0.0);
        return Mono.just(w);
    }

    @Override
    public Flux<movementWallet> listAll() {
        List<movementWallet> lw = new ArrayList<>();
        movementWallet w = new movementWallet();
        w.setTipoMov("34984545");
        w.setCelularEmisor("123456789");
        w.setCelularDestino("987654321");
        w.setMonto(0.0);
        return Flux.fromIterable(lw);
    }

    @Override
    public Flux<movementWallet> listWalletCelularDestino(String celularDestino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mono<movementWallet> create(movementWallet mw) {
        return Mono.just(mw);
    }

    @Override
    public Mono<movementWallet> update(String idMovement, movementWallet mw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String idMovement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
}
