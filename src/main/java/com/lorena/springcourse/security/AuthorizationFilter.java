package com.lorena.springcourse.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorena.springcourse.constant.SecurityConstants;
import com.lorena.springcourse.resource.exception.ApiError;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

public class AuthorizationFilter extends OncePerRequestFilter{ //Garante que cada requisição será tratada de uma vez
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
         

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwt == null || !jwt.startsWith(SecurityConstants.JWT_PROVIDER)){
            ApiError  apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), SecurityConstants.JWT_INVALID_MSG, new Date());
            PrintWriter writer = response.getWriter(); // permite escrever o apiError na resposta
            
            ObjectMapper mapper = new ObjectMapper();
            String apiErrorString = mapper.writeValueAsString(apiError); //Transforma o apiError em String
            
            writer.write(apiErrorString);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            
            return;
    }

    jwt = jwt.replace(SecurityConstants.JWT_PROVIDER, "");
    //Deixando o JWT sozinho

    try{
        Claims claims = new JwtManager().parseToken(jwt);
        String email = claims.getSubject();
        List<String> roles = (List<String>) claims.get(SecurityConstants.JWT_ROLE_KEY);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        roles.forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        });

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch(Exception e){
        ApiError  apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), e.getMessage() , new Date());
        PrintWriter writer = response.getWriter(); // permite escrever o apiError na resposta
        
        ObjectMapper mapper = new ObjectMapper();
        String apiErrorString = mapper.writeValueAsString(apiError); //Transforma o apiError em String
        
        writer.write(apiErrorString);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        return;
    }
}
