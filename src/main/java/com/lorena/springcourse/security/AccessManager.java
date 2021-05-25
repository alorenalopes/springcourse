package com.lorena.springcourse.security;

import java.util.Optional;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.exception.NotFoundException;
import com.lorena.springcourse.repository.UserRepository;
import com.lorena.springcourse.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("accessManager")
public class AccessManager {

    @Autowired private UserRepository userRepository;
    @Autowired private RequestService requestService;
    
    public boolean isOwner(Long id){

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);

        if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);
        
        User user = result.get();
        return user.getId().equals(id);
    
    }

    public boolean isRequestOwner(Long id){

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);

        if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);
        
        User user = result.get();

        Request request = requestService.getById(id);

        return user.getId().equals(request.getOwner().getId());

    }
}
