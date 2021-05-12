package com.lorena.springcourse.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.Date;


import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
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
public class RequestStateRepositoryTests {

    @Autowired private RequestStageRepository requestStageRepository;

    @Test
    public void AsaveTest(){
        User owner = new User();
        owner.setId(1L);
        Request request = new Request();
        request.setId(1L);

        RequestStage stage = new RequestStage(null, new Date(), "Foi comprado um novo laptop HP com 16GB de RAM",  RequestState.CLOSED, request, owner);
        
        RequestStage createdStage = requestStageRepository.save(stage);

        assertEquals(1L, createdStage.getId());
    }
    
    @Test
    public void getByIdTest(){
        Optional<RequestStage> result = requestStageRepository.findById(1L);
        RequestStage stage = result.get();

        assertEquals("Foi comprado um novo laptop HP com 16GB de RAM", stage.getDescription());

    }

    @Test
    public void ListByRequestIdTest(){
        List<RequestStage> stages = requestStageRepository.findAll();

        assertEquals(1, stages.size());
    }
}
