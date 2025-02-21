package com.frizelo.prodGestor.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frizelo.prodGestor.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {

}
