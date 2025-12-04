package com.order.security;

import com.order.dtos.AuthPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            String token=header.substring(7);
            try{
                String username=jwtUtil.getUsername(token);
                List<String> roles=jwtUtil.getRoles(token);
                Long UserId=jwtUtil.getUserId(token);
                List<GrantedAuthority> authorities=roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                AuthPrincipal principal=new AuthPrincipal(UserId,username);
                Authentication authentication=new UsernamePasswordAuthenticationToken(principal,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("hello1");

            } catch (Exception e) {
                System.out.println("hello");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return ;
            }

        }
        filterChain.doFilter(request,response);
    }
}
