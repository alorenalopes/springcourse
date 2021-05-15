package com.lorena.springcourse.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.domain.enums.Role;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Executando os testes em ordem alfabética
@SpringBootTest
public class UserRepositoryTests{

    @Autowired private UserRepository userRepository;

    @Test
    public void AsaveTest(){
        User user = new User(null, "Lorena", "lorena@gmail.com", "123", Role.ADMINISTRADOR, null, null);
        User createdUser = userRepository.save(user);

        assertEquals(1L, createdUser.getId());
    }

    @Test
    public void updateTest(){
        User user = new User(1L, "Lorena Lopes", "lorena@gmail.com", "123", Role.ADMINISTRADOR, null, null);
        User updatedUser = userRepository.save(user);

        assertEquals("Lorena Lopes", updatedUser.getName());

    }

    @Test
    public void getByIdTest(){
        Optional<User> result = userRepository.findById(1L);
        User user = result.get();

        assertEquals(1L, user.getId());
        
    }

    @Test
    public void listTest(){
        List<User> users = userRepository.findAll();
        
        assertEquals(1, users.size());

    }

    @Test
    public void loginTest(){
        Optional<User> result = userRepository.login("lorena@gmail.com", "123");
        User loggedUser = result.get();

        assertEquals(1L, loggedUser.getId());

    }

    @Test

    public void updateRole(){
        User user = new User(1L, "Lorena Lopes", "lorena@gmail.com", "123", Role.ADMINISTRADOR, null, null);
        int affectedRows = userRepository.updateRole(user.getId(), Role.SIMPLE);

        assertEquals(1, affectedRows);
        
    }
}

