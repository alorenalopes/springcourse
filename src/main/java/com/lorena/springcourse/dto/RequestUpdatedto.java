package com.lorena.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RequestUpdatedto {
    
    @NotBlank(message= "Subject required")
    private String subject;
    private String description;

    @NotNull(message = "State required")
    private RequestState state;

    @NotNull(message = "Owner required")
    private User owner;
    private List<RequestStage> stages = new ArrayList<>();

    public Request transformtoRequest(){
        return new Request(null, this.subject, this.description, null, this.state, this.owner, stages);

    }
}
