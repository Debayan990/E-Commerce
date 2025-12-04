package com.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResponseDTO {

    private double totalPrice;

    List<CartItemResponseDTO> items;
}
