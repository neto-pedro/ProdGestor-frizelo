package com.frizelo.prodGestor.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frizelo.prodGestor.excecao.SetorNotFoundException;
import com.frizelo.prodGestor.excecao.UsuarioNotFoundException;
import com.frizelo.prodGestor.modelo.Setor;
import com.frizelo.prodGestor.modelo.Usuario;
import com.frizelo.prodGestor.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario criarUsuario(Usuario usuario){
        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> buscarTodosUsuarios(){
        return usuarioRepositorio.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) throws UsuarioNotFoundException{

        Optional<Usuario> opt = usuarioRepositorio.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        else{
            throw new UsuarioNotFoundException("Usuario com id : " + id + "não existe");
        }
    }

    public void apagarUsuario(Long id) throws UsuarioNotFoundException{
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepositorio.delete(usuario);
    }

    public Usuario alterarUsuario(Usuario usuario){
        return usuarioRepositorio.save(usuario);
    }
}
