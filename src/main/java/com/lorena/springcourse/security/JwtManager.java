package com.lorena.springcourse.security;

import java.util.Calendar;
import java.util.List;

import com.lorena.springcourse.constant.SecurityConstants;
import com.lorena.springcourse.dto.UserLoginResponsedto;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

    public UserLoginResponsedto createToken(String email, List <String> roles){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXP_DAYS);
        
        String jwt = Jwts.builder().setSubject(email)
                                   .setExpiration(calendar.getTime())
                                   .claim(SecurityConstants.JWT_ROLE_KEY, roles)
                                   .signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY.getBytes())
                                   .compact();

        Long expireIn = calendar.getTimeInMillis();

        return new UserLoginResponsedto(jwt, expireIn, SecurityConstants.JWT_PROVIDER);

    }

    public  Claims parseToken(String jwt) throws JwtException{
        return Jwts.parser()
                            .setSigningKey(SecurityConstants.API_KEY.getBytes())
                            .parseClaimsJws(jwt) //Método utilizado quando é certo que o parâmetro passado teve um processo de criptografia
                            .getBody();
    }
    
}
