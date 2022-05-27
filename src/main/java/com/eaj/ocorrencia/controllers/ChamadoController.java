package com.eaj.ocorrencia.controllers;

import com.eaj.ocorrencia.models.Chamado;
import com.eaj.ocorrencia.models.Problema;
import com.eaj.ocorrencia.models.Setor;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.services.ChamadoService;
import com.eaj.ocorrencia.services.ProblemaService;
import com.eaj.ocorrencia.services.SetorService;
import com.eaj.ocorrencia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ChamadoController {

        @Autowired
        ProblemaService problemaService;

        @Autowired
        SetorService setorService;

        @Autowired
        UserService userService;

        @Autowired
        ChamadoService chamadoService;



    @GetMapping(value = "/listar/chamados")
    public ModelAndView listarChamados(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);


        List<Chamado> chamados = chamadoService.getAll();
        modelAndView.addObject("chamados", chamados);


        modelAndView.setViewName("chamado/chamados");
        return modelAndView;
    }

    @GetMapping(value = "admin/chamados")
    public ModelAndView meusChamados(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);

        List<Chamado> chamados = chamadoService.getAll();
        modelAndView.addObject("chamados", chamados);


        modelAndView.setViewName("chamado/admin-chamados");
        return modelAndView;
    }


    @GetMapping(value = "/cadastro/chamado")
    public ModelAndView createSetor(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);

        Chamado chamado = new Chamado();
        modelAndView.addObject("chamado",chamado);

        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);

        List<Problema> problemas = problemaService.getAll();
        modelAndView.addObject("problemas", problemas);

        modelAndView.setViewName("chamado/cadastro-chamado");

        return modelAndView;
    }

    @PostMapping(value = "/cadastro/chamado")
    public ModelAndView createNewProblema(Chamado chamado){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);
        chamado.setUserOpen(user);
        chamadoService.insert(chamado);
        modelAndView.addObject("successMessage", "Chamado cadastrado com sucesso");
        Chamado chamado1 = new Chamado();
        modelAndView.addObject("chamado", chamado1);
        modelAndView.setViewName("chamado/cadastro-chamado");

        return modelAndView;
    }


    @RequestMapping("/admin/atender/chamado/{id}")
    public String atenderChamado(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes){
        Chamado chamado = chamadoService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        chamado.setUserClose(user);
        chamadoService.update(chamado);
        return "redirect:/admin/chamados";
    }


}
