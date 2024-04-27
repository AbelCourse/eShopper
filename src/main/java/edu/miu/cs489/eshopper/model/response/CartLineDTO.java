package edu.miu.cs489.eshopper.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartLineDTO {

    private ProductDTO product;
    private int quantity;
    private double productPrice;
}
