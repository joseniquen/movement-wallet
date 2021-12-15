package com.example.walletmovementmicroservice.domain;

import lombok.Data;

@Data
public class Wallet {
    public String person;
    public String document;
    public String documentType;
    public String telephone;
    public String imei;
    public String mail;
    public String date;
    public boolean state;
}
