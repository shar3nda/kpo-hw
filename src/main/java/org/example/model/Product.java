package org.example.model;

import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Unsigned
    public int Id;
    public ProductType Type;
    public String Name;
    public String Company;
    public enum Unit {
        KG,
        L,
        PCS
    }
    public Unit Unit;
    public double Quantity;
    public double Cost;
    public Date DeliveryDate;
    public Date ExpirationDate;
}
