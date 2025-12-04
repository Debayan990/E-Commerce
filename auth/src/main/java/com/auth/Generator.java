package com.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Generator {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String pass="Harshit";
        String encodedPass=passwordEncoder.encode(pass);
        System.out.println(encodedPass);
    }
}
