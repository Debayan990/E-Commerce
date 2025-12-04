package com.product.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${app.secret.key}")
    private String secretKey;
    @Value("${app.expiry}")
    private long expiry;

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(Authentication authentication,Long id){
        String username=authentication.getName();
        List<String> roles=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toUnmodifiableList());
        Date current=new Date();
        Date expiration=new Date(current.getTime()+expiry);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .claim("userid",id)
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(key())
                .compact();

    }

    private Claims extractToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token){
        return extractToken(token).getSubject();
    }
    public List<String> getRoles(String token){
        return extractToken(token).get("roles",List.class);
    }
    public Long getUserId(String token){
        return extractToken(token).get("userid", Long.class);
    }



}
