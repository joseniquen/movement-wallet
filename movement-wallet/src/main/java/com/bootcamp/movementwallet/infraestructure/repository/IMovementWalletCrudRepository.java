package com.bootcamp.movementwallet.infraestructure.repository;

import com.bootcamp.movementwallet.infraestructure.modelDao.movementWalletDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IMovementWalletCrudRepository extends ReactiveCrudRepository<movementWalletDao,String> {
    Flux<movementWalletDao> findAllByCelularDestino(String celularDestino);
}
