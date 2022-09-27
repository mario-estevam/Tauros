package com.eaj.tauros.repositories;

import com.eaj.tauros.models.Problema;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemaRepository extends JpaRepository<Problema, Long> {
    List<Problema> findAllByDeleteIsNull();
}
