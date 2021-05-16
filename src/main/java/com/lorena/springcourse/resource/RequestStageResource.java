package com.lorena.springcourse.resource;

import javax.validation.Valid;

import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.dto.RequestStageSavedto;
import com.lorena.springcourse.service.RequestStageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {
    @Autowired private RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSavedto requestdto){
        RequestStage requestStage = requestdto.transformToRequestStage();
        RequestStage createdRequestStage = requestStageService.save(requestStage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RequestStage> getById(@PathVariable(name = "id") Long id){
        RequestStage requestStage = requestStageService.getById(id);
        return ResponseEntity.ok(requestStage);
    }
}
