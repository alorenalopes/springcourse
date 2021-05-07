package com.lorena.springcourse.domain;

import java.util.ArrayList;
import java.util.List;

import com.lorena.springcourse.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User{
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private List<Request> requests = new ArrayList<Request>();
    private List<RequestState> stages = new ArrayList<RequestState>();
}