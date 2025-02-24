package com.frizelo.prodGestor.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frizelo.prodGestor.modelo.Papel;

public interface PapelRepositorio extends JpaRepository<Papel, Long>{
    Papel findyByPapel(String papel);

}
