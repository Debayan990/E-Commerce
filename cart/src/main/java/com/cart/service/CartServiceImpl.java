package com.cart.service;

import com.cart.client.ProductClient;
import com.cart.dtos.CartInputDTO;
import com.cart.dtos.CartItemResponseDTO;
import com.cart.dtos.CartResponseDTO;
import com.cart.dtos.ProductResponseDTO;
import com.cart.entities.Cart;
import com.cart.entities.CartItems;
import com.cart.exception.CartNotFoundException;
import com.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    public CartResponseDTO getAll(Long userId) {
        Cart cart=cartRepository.findByUserId(userId)
                .orElseThrow(()->new CartNotFoundException("No Cart Available"));
        CartResponseDTO responseDTO=new CartResponseDTO();
        responseDTO.setTotalPrice(cart.getTotalPrice());
        List<CartItemResponseDTO> responseDTOS=cart.getItems().stream()
                .map(ele->{
                    CartItemResponseDTO dto=new CartItemResponseDTO();
                    dto.setProductId(ele.getProductId());
                    dto.setQuantity(ele.getQuantity());
                    dto.setPrice(ele.getPrice());
                    return dto;
                }).toList();
        responseDTO.setItems(responseDTOS);
        return responseDTO;
    }

    @Override
    public String addToCart(Long userId,CartInputDTO dto) {
        Cart cart=cartRepository.findByUserId(userId).orElseGet(()-> {
            Cart c = new Cart();
            c.setUserId(userId);
            c.setTotalPrice(0);
            c.setItems(new ArrayList<>());
            return cartRepository.save(c);

        });

        ProductResponseDTO responseDTO=productClient.getById(dto.getProductId()).getBody();


        CartItems cartItems=cart.getItems().stream()
                .filter(ele->ele.getProductId().equals(dto.getProductId()))
                .findFirst()
                .orElse(null);
        if(cartItems!=null){
            cartItems.setQuantity(cartItems.getQuantity()+ dto.getQuantity());
        }
        else{
            CartItems newCardItem=new CartItems();
            newCardItem.setPrice(responseDTO.getPrice());
            newCardItem.setQuantity(dto.getQuantity());
            newCardItem.setProductId(dto.getProductId());
            newCardItem.setCart(cart);
            cart.getItems().add(newCardItem);
        }
        double totalPrice=cart.getItems().stream()
                .mapToDouble(ele->ele.getPrice()* ele.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        return "Cart Added Sucessfully";
    }
    @Transactional
    @Override
    public String removeFromCart(Long userId) {
        cartRepository.deleteByUserId(userId);

        return "Items removed from cart";
    }


}
