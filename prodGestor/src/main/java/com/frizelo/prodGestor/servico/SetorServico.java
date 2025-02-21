package com.frizelo.prodGestor.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frizelo.prodGestor.excecao.SetorNotFoundException;
import com.frizelo.prodGestor.modelo.Setor;
import com.frizelo.prodGestor.repositorio.SetorRepositorio;

@Service
public class SetorServico {

    @Autowired
    private SetorRepositorio setorRepositorio;

    public Setor criarSetor(Setor setor){
        return setorRepositorio.save(setor);
    }

    public List<Setor> buscarTodosSetores(){
        return setorRepositorio.findAll();
    }

    public Setor buscarSetorPorId(Long id) throws SetorNotFoundException{

        Optional<Setor> opt = setorRepositorio.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        else{
            throw new SetorNotFoundException("Boiadeiro com id : " + id + "não existe");
        }
    }

    public void apagarSetor(Long id) throws SetorNotFoundException{
        Setor setor = buscarSetorPorId(id);
        setorRepositorio.delete(setor);
    }

    public Setor alterarSetor(Setor setor){
        return setorRepositorio.save(setor);
    }

}
