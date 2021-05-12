package com.lorena.springcourse.repository;

import java.util.List;

import com.lorena.springcourse.domain.RequestStage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {
    
    public List<RequestStage> findAllByRequestId(Long id);

}
