package com.cart.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartInputDTO {

    private Long productId;

    private Integer quantity;

    private double price;

}
