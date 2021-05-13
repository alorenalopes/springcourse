package com.lorena.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.domain.enums.RequestState;
import com.lorena.springcourse.repository.RequestRepository;
import com.lorena.springcourse.repository.RequestStageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStageService {
    
    @Autowired private RequestStageRepository requestStageRepository;
    @Autowired private RequestRepository requestRepository;

    public RequestStage save(RequestStage stage){
        stage.setRealizationDate(new Date());

        RequestStage requestStage = requestStageRepository.save(stage);

        Long requestId = stage.getRequest().getId();
        RequestState requestState = stage.getState();

        requestRepository.updateStatus(requestId, requestState);

        return requestStage;
    }

    public RequestStage getById(Long id){

        Optional<RequestStage> requestStage = requestStageRepository.findById(id);
        
        return requestStage.get();

    }

    public List<RequestStage> listAllByRequestId(Long id){

        return requestStageRepository.findAllByRequestId(id);
    }
}
