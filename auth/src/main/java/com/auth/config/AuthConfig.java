package com.auth.config;

import com.auth.service.CustomSellerDetailService;
import com.auth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AuthConfig {

    private final CustomUserDetailService customUserDetailService;
    private final CustomSellerDetailService customSellerDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public AuthenticationManager userAuthenticationManager(PasswordEncoder encoder) {
        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider(customUserDetailService);
        userProvider.setPasswordEncoder(encoder);
        return new ProviderManager(userProvider);
    }
    @Bean
    public AuthenticationManager sellerAuthenticationManager(PasswordEncoder encoder){
        DaoAuthenticationProvider sellerProvider=new DaoAuthenticationProvider(customSellerDetailService);
        sellerProvider.setPasswordEncoder(encoder);
        return new ProviderManager(sellerProvider);
    }




}
