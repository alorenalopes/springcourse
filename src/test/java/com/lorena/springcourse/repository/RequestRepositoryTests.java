package com.lorena.springcourse.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.domain.enums.RequestState;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class RequestRepositoryTests {

    @Autowired private RequestRepository requestRepository;

    @Test
    public void AsaveTest(){
        User owner = new User();
        owner.setId(1L);

        Request request = new Request(null, "Novo Laptop HP", "Pretendo obter um laptop HP", new Date(), RequestState.OPEN, owner, null);

        Request createdRequest = requestRepository.save(request);

        assertEquals(1L, createdRequest.getId());

    }

    @Test
    public void updateTest(){
        User owner = new User();
        owner.setId(1L);

        Request request = new Request(1L, "Novo Laptop HP", "Pretendo obter um laptop HP, de 16GB de RAM", null, RequestState.OPEN, owner, null);

        Request updatedRequest = requestRepository.save(request);

        assertEquals("Pretendo obter um laptop HP, de 16GB de RAM", updatedRequest.getDescription());

    }

    @Test
    public void getByIdTest(){
        Optional<Request> result = requestRepository.findById(1L);
        Request request = result.get();

        assertEquals("Novo Laptop HP", request.getSubject());

    }

    @Test
    public void listTest(){
        List<Request> requests = requestRepository.findAll();

        assertEquals(1L, requests.size());
    }

    @Test
    public void listByOwnerIdTest(){
        List<Request> requests = requestRepository.findAllByOwnerId(1L);

        assertEquals(1L, requests.size());
    }
    
    @Test
    public void updateStatusTest(){
        int affectedRows = requestRepository.updateStatus(1L, RequestState.IN_PROGRESS);

        System.out.println(affectedRows);

        assertEquals(1L, affectedRows);
    }
}
