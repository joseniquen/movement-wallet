package com.bootcamp.movementwallet.domain;

import lombok.Data;

@Data
public class movementWallet {
    public String celularEmisor;
    public String tipoMov ;
    public String celularDestino;
    public Double monto;
}
