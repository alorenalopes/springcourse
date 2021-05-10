package com.lorena.springcourse.repository;

import java.util.List;
import com.lorena.springcourse.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{ //Nome da classe que o repository pertence e o tipo de dados da chave primária
    
    public List<Request> findAllOwnerId(Long id);
}
