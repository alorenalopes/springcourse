package com.lorena.springcourse.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HashUtilTests {
    
    @Test
    public void getSecureHashTest(){
        String hash = HashUtil.getSecureHash("123");

        System.err.println(hash);

        assertEquals(64, hash.length());
    }
}

//Não utilizou o @Springboot porque está testando uma classe simples. Normalmente, é utilizado quando 
// é necessário usar algum repository ou service no teste