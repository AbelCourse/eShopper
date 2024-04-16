package edu.miu.cs489.eshopper.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    
    private String name;
    private String description;
    private double price;
    private int quantity;
}
