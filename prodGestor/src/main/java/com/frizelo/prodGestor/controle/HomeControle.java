package com.frizelo.prodGestor.controle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeControle {
    
    @RequestMapping("/")   
    public String index(Model model){
        model.addAttribute("bemVindo", "Seja bem vindo ao Sistema de Gestão e Desempenho - Frizelo");
        return "publica-index";
    }

}
