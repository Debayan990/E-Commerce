package com.auth.service;

import com.auth.dtos.*;

public interface AuthService {
    RegisterOutput register(UserInputDTO dto);
    UserOutputDTO login(UserInputDTO dto);
    RegisterOutput sellerRegister(SellerRegister dto);
    SellerOutputDTO sellerLogin(SellerLoginDTO dto);



}
