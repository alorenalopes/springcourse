package com.lorena.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdatedto {

    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Invalid email adress")
    private String email;

    @Size(min = 7, max = 50, message = "Password must be between 7 and 50")
    private String password;
    
    private List<Request> requests = new ArrayList<>();
    private List<RequestStage> stages = new ArrayList<>();

    public User transformToUser(){
        return new User(null, this.name, this.email, this.password, null, this.requests, this.stages);
    }
    
}
