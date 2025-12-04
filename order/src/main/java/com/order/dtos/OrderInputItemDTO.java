package com.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInputItemDTO {

    private Long productId;

    private Integer quantity;

    private double price;


}
