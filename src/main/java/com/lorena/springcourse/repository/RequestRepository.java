package com.lorena.springcourse.repository;

import java.util.List;


import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.enums.RequestState;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{ //Nome da classe que o repository pertence e o tipo de dados da chave primária
    
    public List<Request> findAllByOwnerId(Long id);

    @Transactional(readOnly = false)
    @Modifying //Informa que essa consulta irá modificar a entidade
    @Query("UPDATE request SET state = ?2 WHERE id = ?1")
    public int updateStatus(Long id, RequestState state);
}
