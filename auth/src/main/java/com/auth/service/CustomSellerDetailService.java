package com.auth.service;

import com.auth.entities.Seller;
import com.auth.entities.User;
import com.auth.repository.SellerRepository;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomSellerDetailService implements UserDetailsService {

    private final SellerRepository sellerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller=sellerRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Username not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(seller.getUsername())
                .password(seller.getPassword())
                .authorities(seller.getRole())
                .build();
    }
}
