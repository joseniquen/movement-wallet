package com.bootcamp.movementwallet.infraestructure.rest;

import com.bootcamp.movementwallet.application.MovementWalletOperations;
import com.bootcamp.movementwallet.domain.movementWallet;
import com.bootcamp.movementwallet.utils.MovementType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movement-wallet")
@RequiredArgsConstructor
public class MovementWalletController {
    private final MovementWalletOperations operations;

    @GetMapping
    public Flux<movementWallet> listAll() {
        return operations.listAll();
    }

    @GetMapping("/{celularEmisor}")
    public Mono<movementWallet> get(@PathVariable("celularEmisor") String celularEmisor) {
        return operations.get(celularEmisor);
    }

    @GetMapping("/{celularDestino}/list")
    public Flux<movementWallet> listWalletCelularDestino(@PathVariable("celularDestino") String celularDestino) {
        return operations.listWalletCelularDestino(celularDestino);
    }

    @PostMapping
    public Mono<ResponseEntity> create(@RequestBody movementWallet c) {
        c.setCelularEmisor(c.getCelularEmisor());
        return Mono.just(c).flatMap(m -> {
            boolean isMovementType = false;
                for (MovementType tc : MovementType.values()) {
                    if (c.getTipoMov().equals(tc.value)) {
                        isMovementType = true;
                    }
                }
                if (!isMovementType){
                        return Mono.just(ResponseEntity.ok("El codigo de Tipo Movimiento (" + c.getTipoMov() + "), no existe!"));

                }
                    return operations.create(c).flatMap(rp -> {
                        return Mono.just(ResponseEntity.ok(rp));
            });
        });
    }

    @PutMapping("/{celular}")
    public Mono<movementWallet> update(@PathVariable("celular") String celular, @RequestBody movementWallet c) {
        return operations.update(celular, c);
    }

    @DeleteMapping("/{celular}")
    public void delete(@PathVariable("celular") String celular) {
        operations.delete(celular);
    }

}
