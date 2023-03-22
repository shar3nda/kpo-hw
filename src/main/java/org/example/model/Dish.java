package org.example.model;

import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Unsigned
    public int Id;
    public String Name;
    public String Description;
    public double CookingTime;
    public List<Equipment> Equipment;
    public List<Operation> Operations;
}
