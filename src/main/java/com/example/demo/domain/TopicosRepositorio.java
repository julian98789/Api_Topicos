package com.example.demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicosRepositorio extends JpaRepository<Topicos, Long> {

    Page<Topicos> findByActiveTrue(Pageable paginacion);

}
