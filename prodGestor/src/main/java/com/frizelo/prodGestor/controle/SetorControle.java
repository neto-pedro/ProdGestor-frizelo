package com.frizelo.prodGestor.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frizelo.prodGestor.excecao.SetorNotFoundException;
import com.frizelo.prodGestor.modelo.Setor;
import com.frizelo.prodGestor.servico.SetorServico;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/setor")
public class SetorControle {

    @Autowired
    private SetorServico setorServico;

    @GetMapping("/novo")
    public String novoSetor(Model model){
        Setor setor = new Setor();
        model.addAttribute("novoSetor", setor);
        
        List<Setor> setores  = setorServico.buscarTodosSetores(); // Mostrar a lista 
		model.addAttribute("listaSetores, setores");

        return "/auth/admin/admin-setor";  
    }

    @PostMapping("/gravar")
    public String gravarSetor(@ModelAttribute("novoSetor") @Valid  Setor setor, BindingResult erros, Model model,
    RedirectAttributes attributes){
     
        if (erros.hasErrors()) {
            // Recarregar a lista de veículos em caso de erro de validação
            List<Setor> setores= setorServico.buscarTodosSetores();
        model.addAttribute("listaSetores", setores);
            return "/auth/admin/admin-setor";
        }
        setorServico.criarSetor(setor);
        attributes.addFlashAttribute("mensagem", "Setor salvo com sucesso!");
        return "redirect:/setor/novo";
    }

    @PostMapping("/buscar")
    public String buscarSetores(Model model, @RequestParam("query") String query) {	
		
        if (query == null) {
			return "redirect:/setor/novo";
		}
        //List<Setor> setores = setorServico.buscarPorQuery(query);
        //model.addAttribute("listaSetores", setores);

        Setor setor = new Setor();
        model.addAttribute("novoSetor", setor);
		return "/auth/admin/admin-setor";
    }

    @GetMapping("/apagar/{id}")
    public String apagarSetor(@PathVariable ("id") long id, RedirectAttributes attributes){
       try {
        setorServico.apagarSetor(id);
    } catch (SetorNotFoundException e) {
        attributes.addFlashAttribute("mensagemErro", e.getMessage());
    }
        return "redirect:/setor/novo";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable ("id") long id, RedirectAttributes attributes, Model model){
       try {
        Setor setor = setorServico.buscarSetorPorId(id);
        model.addAttribute("objetoSetor", setor);
        return "/auth/admin/admin-setor-editar";
    } catch (SetorNotFoundException e) {
        attributes.addFlashAttribute("mensagemErro", e.getMessage());
    }
        return "redirect:/setor/novo";
    }
    
    
    @PostMapping("/editar/{id}")
    public String editarSetor(@PathVariable("id") long id, @ModelAttribute("objetoSetor")@Valid Setor setor, BindingResult erros){
        if (erros.hasErrors()){
            setor.setId(id);
            return"/Setor/editar-Setor";
        }
        setorServico.alterarSetor(setor);
        return"redirect:/Setor/novo";
    }



     
}
