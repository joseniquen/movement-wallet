package com.bootcamp.movementwallet.infraestructure.modelDao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("movementWallet")
public class movementWalletDao {
    @Id
    public String celularEmisor;
    public String tipoMov ;
    public String celularDestino;
    public Double monto;
}
