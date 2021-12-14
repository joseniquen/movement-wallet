package com.bootcamp.movementwallet.infraestructure.repository;

import com.bootcamp.movementwallet.application.model.MovementWalletRepository;
import com.bootcamp.movementwallet.domain.movementWallet;
import com.bootcamp.movementwallet.infraestructure.modelDao.movementWalletDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovementWalletCrudRepository implements MovementWalletRepository {
    @Autowired
    IMovementWalletCrudRepository iMovementWalletCrudRepository;

    @Override
    public Mono<movementWallet> get(String celularEmisor) {
        return iMovementWalletCrudRepository.findById(celularEmisor).map(this::movementWalletDaoToMovementWallet);

    }

    @Override
    public Flux<movementWallet> listAll() {
        return iMovementWalletCrudRepository.findAll().map(this::movementWalletDaoToMovementWallet);
    }


    @Override
    public Flux<movementWallet> listWalletCelularDestino(String celularDestino) {
        return iMovementWalletCrudRepository.findAllByCelularDestino(celularDestino).map(this::movementWalletDaoToMovementWallet);
    }

    @Override
    public Mono<movementWallet> create(movementWallet mw) {
        return iMovementWalletCrudRepository.save(movementWalletToMovementWalletDao(mw)).map(this::movementWalletDaoToMovementWallet);

    }

    @Override
    public Mono<movementWallet> update(String celularEmisor, movementWallet mw) {
        mw.setCelularEmisor(celularEmisor);
        return iMovementWalletCrudRepository.save(movementWalletToMovementWalletDao(mw)).map(this::movementWalletDaoToMovementWallet);
    }

    @Override
    public void delete(String celularEmisor) {
        iMovementWalletCrudRepository.deleteById(celularEmisor).subscribe();

    }

    public movementWallet movementWalletDaoToMovementWallet(movementWalletDao ad) {
        movementWallet movementWallet = new movementWallet();
        movementWallet.setTipoMov(ad.getTipoMov());
        movementWallet.setCelularEmisor(ad.getCelularEmisor());
        movementWallet.setCelularDestino(ad.getCelularDestino());
        movementWallet.setMonto(ad.getMonto());
        return movementWallet;

    }

    public movementWalletDao movementWalletToMovementWalletDao(movementWallet w) {
        movementWalletDao movementWalletDao = new movementWalletDao();
        movementWalletDao.setTipoMov(w.getTipoMov());
        movementWalletDao.setCelularEmisor(w.getCelularEmisor());
        movementWalletDao.setCelularDestino(w.getCelularDestino());
        movementWalletDao.setMonto(w.getMonto());
        return movementWalletDao;
    }
}
