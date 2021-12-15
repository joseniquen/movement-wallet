package com.example.walletmovementmicroservice.aplication.impl;

import com.example.walletmovementmicroservice.aplication.MovementOperations;
import com.example.walletmovementmicroservice.aplication.WalletOperations;
import com.example.walletmovementmicroservice.aplication.model.MovementRepository;
import com.example.walletmovementmicroservice.domain.Movement;
import com.example.walletmovementmicroservice.utils.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementOperationsImpl implements MovementOperations {
    Logger logger = LoggerFactory.getLogger(WalletOperationsImpl.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Bogota"));

    public ResponseService responseService;
    private final MovementRepository movementRepository;
    private final WalletOperations walletOperations;

    @Override
    public Flux<Movement> list() {
        return movementRepository.list();
    }

    @Override
    public Mono<Movement> get(String movement) {
        return movementRepository.get(movement);
    }

    @Override
    public Mono<ResponseService> create(Movement movement) {
        return validateDataWalletToCreate(movement).flatMap(RS -> {
            responseService = RS;
            if (responseService.getStatus() == Status.OK) {
                return walletOperations.get(movement.getProduct()).flatMap(walletR -> {
                    return insertMovement(movement);
                }).switchIfEmpty(errorNotExistWallet(movement));
            } else {
                return Mono.just(responseService);
            }
        });

    }

    @Override
    public Mono<Movement> update(String movement, Movement c) {
        return movementRepository.update(movement, c);
    }

    @Override
    public void delete(String movement) {
        movementRepository.delete(movement);
    }

    @Override
    public Flux<Movement> listByCustomer(String customer) {
        return movementRepository.listByCustomer(customer);
    }

    @Override
    public Flux<Movement> listByCustomerAndProduct(String customer, String product) {
        return movementRepository.listByCustomerAndAccount(customer, product);
    }

    @Override
    public Flux<Movement> listByProduct(String product) {

        return movementRepository.listByProduct(product);
    }

    @Override
    public Mono<Double> mainAccountBalance(String debitCard) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Mono<ResponseService> insertMovement(Movement movement) {
        responseService = new ResponseService();

        movement.setMovement(getRandomNumberString());
        movement.setCustomer(movement.getProduct());

        Optional<Concept> con = Arrays.stream(Concept.values())
                .filter(w -> w.toString().toUpperCase().equals(movement.getConcept().toUpperCase()))
                .findFirst();
        Concept concept = con.isPresent() ? con.get() : Concept.SACAR_DINERO;
        if (concept.movementType == MovementType.ABONO) {
            movement.setMovementType(MovementType.ABONO.toString());
            movement.setModality(Modality.VENTANILLA.toString());
            movement.setConcept(Concept.PONER_DINERO.toString());
            movement.setProductType(ProductType.MONEDERO_MOVIL.toString());

            if (movement.getAmount() < 0) {
                movement.setAmount(movement.getAmount() * -1);
            }

        } else {

            if (movement.getAmount() > 0) {
                movement.setAmount(movement.getAmount() * -1);
            }
            movement.setMovementType(MovementType.CARGO.toString());
            movement.setModality(Modality.VENTANILLA.toString());
            movement.setConcept(concept.toString());
            movement.setProductType(ProductType.MONEDERO_MOVIL.toString());

            movement.setObservation(concept.value + ", por la suma de " + movement.getAmount() + " Soles.");
        }

        movement.setThirdClient("");
        movement.setThirdProduct("");

        movement.setDate(dateTime.format(formatDate));
        movement.setHour(dateTime.format(formatTime));
        movement.setState(true);

        if (concept.movementType == MovementType.ABONO) {
            return movementRepository.create(movement).flatMap(w -> {
                responseService.setStatus(Status.OK);
                responseService.setData(w);
                return Mono.just(responseService);
            });
        } else {
            // validar si hay saldo
            return saldo(movement.getProduct()).flatMap(amout -> {
                if ((amout + movement.getAmount()) < 0) {
                    responseService.setStatus(Status.ERROR);
                    responseService.setData(amout);
                    responseService.setMessage("Saldo insuficiente para realizar esta operacion");
                    return Mono.just(responseService);

                }

                return movementRepository.create(movement).flatMap(w -> {
                    responseService.setStatus(Status.OK);
                    responseService.setData(w);
                    return Mono.just(responseService);
                });

            });
        }
    }

    public Mono<ResponseService> insertDeposito(Movement movement) {
        responseService = new ResponseService();
        movement.setMovement(getRandomNumberString());

        movement.setMovementType(MovementType.ABONO.toString());
        movement.setModality(Modality.VENTANILLA.toString());
        movement.setConcept(Concept.PONER_DINERO.toString());
        movement.setProductType(ProductType.MONEDERO_MOVIL.toString());

        movement.setThirdClient("");
        movement.setThirdProduct("");

        if (movement.getAmount() < 0) {
            movement.setAmount(movement.getAmount() * -1);
        }

        movement.setObservation("Abono de " + movement.getAmount() + " Soles.");
        movement.setDate(dateTime.format(formatDate));
        movement.setHour(dateTime.format(formatTime));
        movement.setState(true);
        return movementRepository.create(movement).flatMap(w -> {
            responseService.setStatus(Status.OK);
            responseService.setData(w);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> validateDataWalletToCreate(Movement movement) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        return Mono.just(movement).flatMap(fm -> {
            if (!Optional.ofNullable(movement.getAmount()).isPresent() || movement.getAmount() == 0) {
                responseService.setMessage("Debe el monto y no debe ser 0");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getProduct()).isPresent() || movement.getProduct().length() < 9) {
                responseService.setMessage("Debe ingresar el telefono afialiado a su monedero, Ejemplo: { \"product\": \"949494465\"}");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getConcept()).isPresent()) {
                responseService.setMessage("Debe ingresar un concepto, Ejemplo: { \"concept\": \"RECARGA DE MI CELULAR\"}");
                return Mono.just(responseService);
            }

            Optional<Concept> concept = Arrays.stream(Concept.values())
                    .filter(w -> w.toString().toUpperCase().equals(movement.getConcept().toUpperCase()))
                    .findFirst();

            if (concept.isEmpty()) {
                responseService.setMessage("Debe ingresar un concepto segun la operacion que desee realizar:");
                String os = Arrays.stream(Concept.values())
                        .map(fc -> fc.value + " =  {\"concept\": \"" + fc.toString() + "\"}")
                        .reduce("", (t, u) -> {
                            return t + "\n" + u;
                        });
                responseService.setMessage(responseService.getMessage() + os);
                return Mono.just(responseService);
            }

            responseService.setStatus(Status.OK);
            responseService.setData(fm);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> validateDataDeposito(Movement movement) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        return Mono.just(movement).flatMap(fm -> {
            if (!Optional.ofNullable(movement.getAmount()).isPresent() || movement.getAmount() == 0) {
                responseService.setMessage("Debe el monto y no debe ser 0");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getProduct()).isPresent() || movement.getProduct().length() < 9) {
                responseService.setMessage("Debe ingresar el telefono afialiado a su monedero, Ejemplo: { \"product\": \"949494465\"}");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getConcept()).isPresent()) {
                responseService.setMessage("Debe ingresar un concepto, Ejemplo: { \"concept\": \"RECARGA DE MI CELULAR\"}");
                return Mono.just(responseService);
            }

            Optional<Concept> concept = Arrays.stream(Concept.values())
                    .filter(w -> w.toString().toUpperCase().equals(movement.getConcept().toUpperCase()))
                    .findFirst();

            if (concept.isEmpty()) {
                responseService.setMessage("Debe ingresar un concepto segun la operacion que desee realizar:");
                String os = Arrays.stream(Concept.values())
                        .map(fc -> fc.value + " =  {\"concept\": \"" + fc.toString() + "\"}")
                        .reduce("", (t, u) -> {
                            return t + "\n" + u;
                        });
                responseService.setMessage(responseService.getMessage() + os);
                return Mono.just(responseService);
            }

            responseService.setStatus(Status.OK);
            responseService.setData(fm);
            return Mono.just(responseService);
        });

    }

    public Mono<ResponseService> validateDataRetiro(Movement movement) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        return Mono.just(movement).flatMap(fm -> {
            if (!Optional.ofNullable(movement.getAmount()).isPresent() || movement.getAmount() <= 0) {
                responseService.setMessage("Debe el monto y no debe ser mayor a 0.00");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getProduct()).isPresent() || movement.getProduct().length() < 9) {
                responseService.setMessage("Debe ingresar el telefono afialiado a su monedero, Ejemplo: { \"product\": \"949494465\"}");
                return Mono.just(responseService);
            }
            responseService.setStatus(Status.OK);
            responseService.setData(fm);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> errorNotExistWallet(Movement movement) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        responseService.setMessage("No existe el wallet " + movement.getProduct());
        responseService.setData(movement);
        return Mono.just(responseService);
    }

    public String getRandomNumberString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public Mono<Double> saldo(String wallet) {
        return movementRepository.listByProduct(wallet).collect(Collectors.summingDouble(Movement::getAmount)).flatMap(e -> {
            return Mono.just(e);
        });
    }
}
