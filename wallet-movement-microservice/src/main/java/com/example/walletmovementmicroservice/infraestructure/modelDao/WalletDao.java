package com.example.walletmovementmicroservice.infraestructure.modelDao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("wallet")
public class WalletDao {

    @Id
    public String telephone;
    public String person;
    public String document;
    public String documentType;
    public String imei;
    public String mail;
    public String date;
    public boolean state;
}
