package com.frizelo.prodGestor.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.frizelo.prodGestor.modelo.Papel;
import com.frizelo.prodGestor.repositorio.PapelRepositorio;

@Component
public class CarregadoraDados implements CommandLineRunner {

    @Autowired
    private PapelRepositorio papelRepositorio;

    public void run(String... args) throws Exception{
        String[] papeis = {"ADMIN", "USER"};

        for (String papelString: papeis) {
			Papel papel = papelRepositorio.findByPapel(papelString);
			if (papel == null) {
				papel = new Papel(papelString);
				papelRepositorio.save(papel);
			}
		}

    }

}
