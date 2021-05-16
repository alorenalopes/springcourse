package com.lorena.springcourse.dto;

import javax.validation.constraints.NotNull;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RequestStageSavedto {

    private String description;

    @NotNull(message = "State required")
    private RequestState state;

    @NotNull(message = "Request required")
    private Request request;

    @NotNull(message = "Owner required")
    private User owner;

    public RequestStage transformToRequestStage(){
         return new RequestStage(null, null, this.description, this.state, this.request, this.owner);
    }
    
}
