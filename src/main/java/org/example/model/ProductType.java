package org.example.model;

import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {
    @Unsigned
    public int Id;
    public String Name;
    public boolean IsFood;
}
