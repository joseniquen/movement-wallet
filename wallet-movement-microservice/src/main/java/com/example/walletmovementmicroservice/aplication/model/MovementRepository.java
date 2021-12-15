package com.example.walletmovementmicroservice.aplication.model;

import com.example.walletmovementmicroservice.domain.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    public Flux<Movement> list();

    public Flux<Movement> listByCustomer(String customer);

    public Flux<Movement> listByProduct(String account);

    public Flux<Movement> listByCustomerAndAccount(String customer, String account);

    public Mono<Movement> get(String movement);

    public Mono<Movement> create(Movement movement);

    public Mono<Movement> update(String id, Movement movement);

    public void delete(String id);
}
