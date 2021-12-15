package com.example.walletmovementmicroservice.infraestructure.modelDao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("movement")
public class MovementDao {
    @Id
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
