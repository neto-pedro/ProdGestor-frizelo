package com.frizelo.prodGestor.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frizelo.prodGestor.excecao.UsuarioNotFoundException;
import com.frizelo.prodGestor.modelo.Setor;
import com.frizelo.prodGestor.modelo.Usuario;
import com.frizelo.prodGestor.repositorio.PapelRepositorio;
import com.frizelo.prodGestor.servico.SetorServico;
import com.frizelo.prodGestor.servico.UsuarioServico;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/usuario")
public class UsuarioControle {

    @Autowired
    private UsuarioServico usuarioServico;

    @Autowired
    private SetorServico setorServico;

    @Autowired
    private PapelRepositorio papelRepositorio;

    @GetMapping("/novo") 
    public String novoUsuario(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("novoUsuario", usuario);

        List<Usuario> usuarios  = usuarioServico.buscarTodosUsuarios(); // Mostrar a lista 
		model.addAttribute("listaUsuarios", usuarios);

        List<Setor> setores = setorServico.buscarTodosSetores(); // Listar setor
		model.addAttribute("setores", setores);

        model.addAttribute("papeis", papelRepositorio.findAll()); // Listar papel

        model.addAttribute("usuarios", new Usuario()); // Lista  de Usuarios na mesma pagina
        return "/auth/admin/admin-criar-usuario";
    }

    @PostMapping("/gravar")
    public String gravarUsuario(@ModelAttribute("novoUsuario") @Valid  Usuario Usuario, BindingResult erros, Model model,
    RedirectAttributes attributes){
     
        if (erros.hasErrors()) {
            // Recarregar a lista de usuarios em caso de erro de validação
            List<Usuario> usuarios= usuarioServico.buscarTodosUsuarios();
            model.addAttribute("listaUsuarios", usuarios);

            List<Setor> setores = setorServico.buscarTodosSetores(); // Listar setor
		    model.addAttribute("setores", setores);

            model.addAttribute("papeis", papelRepositorio.findAll()); // Listar papel

            return "/auth/admin/admin-criar-usuario";
        }
        usuarioServico.criarUsuario(Usuario);
        attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
        return "redirect:/usuario/novo";
    }

    @GetMapping("/apagar/{id}")
    public String apagarUsuario(@PathVariable ("id") long id, RedirectAttributes attributes){
       try {
        usuarioServico.apagarUsuario(id);
    } catch (UsuarioNotFoundException e) {
        attributes.addFlashAttribute("mensagemErro", e.getMessage());
    }
        return "redirect:/usuario/novo";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable ("id") long id, RedirectAttributes attributes, Model model){
       try {
        Usuario usuario = usuarioServico.buscarUsuarioPorId(id);
        model.addAttribute("objetoUsuario", usuario);

        List<Setor> setores = setorServico.buscarTodosSetores(); // Listar setor
		model.addAttribute("setores", setores);

        model.addAttribute("papeis", papelRepositorio.findAll()); // Listar papel

        return "/auth/admin/admin-usuario-editar";

        
    } catch (UsuarioNotFoundException e) {
        attributes.addFlashAttribute("mensagemErro", e.getMessage());
    }
        return "redirect:/usuario/novo";
    }
    
    
    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") long id, @ModelAttribute("objetoUsuario")@Valid Usuario usuario, BindingResult erros, Model model, RedirectAttributes attributes){
        if (erros.hasErrors()){
            usuario.setId(id);
            List<Setor> setores = setorServico.buscarTodosSetores(); // Listar setor
		    model.addAttribute("setores", setores);
            
            model.addAttribute("papeis", papelRepositorio.findAll()); // Listar papel
           
            return"/auth/admin/admin-usuario-editar";
        }
        usuarioServico.alterarUsuario(usuario);
        attributes.addFlashAttribute("mensagem", "Usuário "+ usuario.getNome() +" alterado com sucesso!");
        return"redirect:/usuario/novo";
    }
    
}
