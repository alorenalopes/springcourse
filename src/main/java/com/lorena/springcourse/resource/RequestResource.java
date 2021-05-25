package com.lorena.springcourse.resource;

import java.util.List;

import javax.validation.Valid;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.dto.RequestSavedto;
import com.lorena.springcourse.dto.RequestUpdatedto;
import com.lorena.springcourse.model.PageModel;
import com.lorena.springcourse.model.PageRequestModel;
import com.lorena.springcourse.security.AccessManager;
import com.lorena.springcourse.service.RequestService;
import com.lorena.springcourse.service.RequestStageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

    @Autowired private RequestService requestService;
    @Autowired private RequestStageService requestStageService;
    @Autowired private AccessManager accessManager;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody @Valid RequestSavedto requestdto) {
        Request request = requestdto.transformtoRequest();
        Request createdRequest = requestService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PreAuthorize("@accessManager.isRequestOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Request> update(
        @PathVariable(name = "id") Long id, 
        @RequestBody @Valid RequestUpdatedto requestdto) {
        Request request = requestdto.transformtoRequest();
        request.setId(id);
        Request updatedRequest = requestService.update(request);
        return ResponseEntity.ok(updatedRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getById(@PathVariable(name = "id") Long id) {
        Request request = requestService.getById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping
    public ResponseEntity<List<Request>> listAll() {
        List<Request> requests = requestService.listAll();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}/request-stages")
    public ResponseEntity<PageModel<RequestStage>> listAllStagesById(
        @PathVariable(name = "id") Long id,
        @RequestParam(value = "page",  defaultValue = "0") int page,
        @RequestParam(value = "size",  defaultValue = "10") int size) {

        PageRequestModel pr = new PageRequestModel(page, size);
        
        PageModel<RequestStage> pm = requestStageService.listAllByRequestIdOnLazyMode(id, pr);
        
        return ResponseEntity.ok(pm);
    }

}
