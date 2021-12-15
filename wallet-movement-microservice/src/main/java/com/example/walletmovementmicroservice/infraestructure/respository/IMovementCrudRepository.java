package com.example.walletmovementmicroservice.infraestructure.respository;

import com.example.walletmovementmicroservice.infraestructure.modelDao.MovementDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IMovementCrudRepository  extends ReactiveCrudRepository<MovementDao, String> {

    Flux<MovementDao> findAllByProduct(String product);

    Flux<MovementDao> findAllByCustomer(String customer);

    Flux<MovementDao> findAllByCustomerAndProduct(String customer, String product);
}
