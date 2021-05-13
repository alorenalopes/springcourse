package com.lorena.springcourse.service;

import java.util.List;
import java.util.Optional;

import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.repository.UserRepository;
import com.lorena.springcourse.service.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired private UserRepository userRepository;

    public User save(User user){ 

        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    public User update(User user){
        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    public User getById(Long id ){
        Optional<User> result = userRepository.findById(id);

        return result.get();
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public User login(String email, String password){
        password = HashUtil.getSecureHash(password);
        
        Optional<User> result = userRepository.login(email, password);
        
        return result.get();
    }
}
