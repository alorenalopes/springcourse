package com.lorena.springcourse.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.model.PageModel;
import com.lorena.springcourse.model.PageRequestModel;
import com.lorena.springcourse.repository.UserRepository;
import com.lorena.springcourse.service.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
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

        return result.isPresent() ? result.get():null;

    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public PageModel<User> listAllOnLazyMode(PageRequestModel pr){
        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
        Page<User> page = userRepository.findAll(pageable);

        return new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
    }

    public User login(String email, String password){
        password = HashUtil.getSecureHash(password);
        
        Optional<User> result = userRepository.login(email, password);
        
        return result.isPresent() ? result.get():null;
    }

    public int updateRole(User user){

        return userRepository.updateRole(user.getId(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);

        if(!result.isPresent()) throw new UsernameNotFoundException("Doesn't exist user with email = " + username);
        
        User user = result.get();

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); //.name porque o Role é uma string e "ROLE_" para ser compreendido como uma permissão
        //asList -> Lista pré definida de elementos

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    
    }
}
