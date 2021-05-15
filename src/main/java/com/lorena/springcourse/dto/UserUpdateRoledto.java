package com.lorena.springcourse.dto;

import com.lorena.springcourse.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRoledto {
    private Role role;
}
