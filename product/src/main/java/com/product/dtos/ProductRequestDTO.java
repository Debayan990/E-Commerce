package com.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDTO {


    private String name;

    private String description;

    private double price;

    private String category;

    private int stock;

    private String imageurl;
}
