package com.cart.service;

import com.cart.dtos.CartInputDTO;
import com.cart.dtos.CartResponseDTO;

import java.util.List;

public interface CartService {
    CartResponseDTO getAll(Long userId);
    String addToCart(Long userId,CartInputDTO dto);
    String removeFromCart(Long userId);

}
