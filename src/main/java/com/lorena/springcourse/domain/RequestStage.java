package com.lorena.springcourse.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RequestStage{
    private Long id;
    private Date realizationDate;
    private String description;
    private RequestState state;
    private Request request;
    private User user;

}