package com.example.walletmovementmicroservice.domain;

import lombok.Data;

@Data
public class Movement {

    public String movement;
    public String movementType;
    public String concept;
    public String modality;
    public String product;
    public String productType;
    public String customer;
    public String observation;
    public String thirdClient;
    public String thirdProduct;
    public double amount;
    public String date;
    public String hour;
    public boolean state;
}
