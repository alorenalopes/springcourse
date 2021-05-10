package com.lorena.springcourse.repository;

import java.util.List;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.RequestStage;
import com.lorena.springcourse.domain.enums.RequestState;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {
    
    public List<RequestStage> findAllByRequest(Long id);

    @Query("UPDATE Request SET status = ?2 WHERE id = ?1")
    public Request updateStatus(Long id, RequestState state);
}
