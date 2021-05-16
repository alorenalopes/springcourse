package com.lorena.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RequestSavedto {

    @NotBlank(message = "Subject required")
    private String subject;
    private String description;

    @NotNull(message = "Owner required")
    private User owner;
    private List<RequestStage> stages = new ArrayList<>();

    public Request transformtoRequest(){
        return new Request(null, this.subject, this.description, null, null, this.owner, stages);

    }
}
