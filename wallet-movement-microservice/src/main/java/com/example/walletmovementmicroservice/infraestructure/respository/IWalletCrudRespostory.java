package com.example.walletmovementmicroservice.infraestructure.respository;

import com.example.walletmovementmicroservice.infraestructure.modelDao.WalletDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IWalletCrudRespostory extends ReactiveCrudRepository<WalletDao, String> {
    Flux<WalletDao> findAllByDocument(String document);
}
