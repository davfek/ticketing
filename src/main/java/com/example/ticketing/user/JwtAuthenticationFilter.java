package com.example.ticketing.user;

import com.example.ticketing.config.TokenConfig;
import com.example.ticketing.service.CustomUserDetailsService;
import com.example.ticketing.service.LoginService;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final LoginService loginService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(LoginService loginService, CustomUserDetailsService customUserDetailsService) {


        this.loginService = loginService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        String username = null;
        if (loginService.validateToken(token)) {
            username = loginService.getUserIdFromToken(token);
        }
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = extractToken(request);
//
//        if (token != null && validateToken(token)) {
//            Authentication authentication = new JwtAuthenticationToken(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }

//    private String extractToken(jakarta.servlet.http.HttpServletRequest request){
//        String authorizationHeader=request.getHeader("Authorization");
//        if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
//            return authorizationHeader.substring(7);
//        }
//        return null;
//    }
//
//    private boolean validateToken(String token){
//        try {
//            Jws<Claims> claimsJws = Jwts.parserBuilder()
//                    .setSigningKey(tokenConfig.getSecretKey())
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }


}
