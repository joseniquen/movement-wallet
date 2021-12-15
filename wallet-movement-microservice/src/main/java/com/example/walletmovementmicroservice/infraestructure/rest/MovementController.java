package com.example.walletmovementmicroservice.infraestructure.rest;

import com.example.walletmovementmicroservice.aplication.MovementOperations;
import com.example.walletmovementmicroservice.domain.Movement;
import com.example.walletmovementmicroservice.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mov-wallet")
@RequiredArgsConstructor
public class MovementController {
    private final MovementOperations operations;

    @GetMapping
    public Flux<Movement> listAll() {
        return operations.list();
    }

    @GetMapping("/{id}")
    public Mono<Movement> get(@PathVariable("id") String id) {
        return operations.get(id);
    }

    @GetMapping("/customer/{id}/list")
    public Flux<Movement> listByCustomer(@PathVariable("id") String id) {
        return operations.listByCustomer(id);
    }

    @GetMapping("/{product}/list")
    public Flux<Movement> listByProduct(@PathVariable("product") String product) {
        return operations.listByProduct(product);
    }

    @PostMapping
    public Mono<ResponseEntity> create(@RequestBody Movement rqMovement) {
        return operations.create(rqMovement).flatMap(w -> {
            if (w.getStatus() == Status.OK) {
                return Mono.just(new ResponseEntity(w.getData(), HttpStatus.OK));
            }
            return Mono.just(new ResponseEntity(w.getMessage(), HttpStatus.BAD_REQUEST));
        });
    }

    @PutMapping("/{id}")
    public Mono<Movement> update(@PathVariable("id") String id, @RequestBody Movement movement) {
        return operations.update(id, movement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        operations.delete(id);
    }
}
