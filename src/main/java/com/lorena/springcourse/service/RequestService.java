package com.lorena.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.enums.RequestState;
import com.lorena.springcourse.model.PageModel;
import com.lorena.springcourse.model.PageRequestModel;
import com.lorena.springcourse.repository.RequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired private RequestRepository requestRepository;

    public Request save(Request request){
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());

        return requestRepository.save(request);
    }

    public Request update(Request request){

        return requestRepository.save(request);
    }

    public Request getById(Long id){

        Optional<Request> request = requestRepository.findById(id);

        return request.get();
    }
    
    public List<Request> listAll(){

        return requestRepository.findAll();
    }

    public List<Request> listAllByOwnerId(Long id){

        return requestRepository.findAllByOwnerId(id);
    }

    public PageModel<Request> listAllByOwnerIdOnLazyMode(Long ownerId, PageRequestModel pr){
        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
        Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);

        return new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
    }


}
