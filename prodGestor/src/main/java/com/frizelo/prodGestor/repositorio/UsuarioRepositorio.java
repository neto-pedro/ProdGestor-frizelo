package com.frizelo.prodGestor.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.frizelo.prodGestor.modelo.Usuario;


public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    UserDetails findByEmail(String email);
}
