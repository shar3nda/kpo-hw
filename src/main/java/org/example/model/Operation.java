package org.example.model;

import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    public OperationType Type;
    public double Time;
    public double AsyncPoint;
    public Map<Product, Double> Products;
}
