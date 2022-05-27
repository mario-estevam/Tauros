package com.eaj.ocorrencia.controllers;


import com.eaj.ocorrencia.models.Problema;
import com.eaj.ocorrencia.models.Setor;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.services.ProblemaService;
import com.eaj.ocorrencia.services.SetorService;
import com.eaj.ocorrencia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProblemaController {

    @Autowired
    ProblemaService problemaService;

    @Autowired
    SetorService setorService;

    @Autowired
    UserService userService;


    @GetMapping(value = "/admin/listar/problemas")
    public ModelAndView listSetores(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);
        List<Problema> problemas = problemaService.getAll();
        modelAndView.addObject("problemas", problemas);
        modelAndView.setViewName("problema/problemas");
        return modelAndView;
    }

    @GetMapping(value = "/admin/cadastro/problema")
    public ModelAndView createSetor(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);
        Problema problema = new Problema();
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        modelAndView.addObject("problema", problema);
        modelAndView.setViewName("problema/cadastro-problema");
        return modelAndView;
    }

    @PostMapping(value = "/admin/cadastro/problema")
    public ModelAndView createNewProblema(Problema problema){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);
        problemaService.insert(problema);
        modelAndView.addObject("successMessage", "Problema cadastrado com sucesso");
        Problema problema1 = new Problema();
        modelAndView.addObject("problema", problema1);
        modelAndView.setViewName("problema/cadastro-problema");

        return modelAndView;
    }

    @RequestMapping("/admin/deletar/problema/{id}")
    public String doDelete(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes){
        problemaService.delete(id);
        redirectAttributes.addAttribute("msg", "Deletado com sucesso");
        return "redirect:/admin/listar/problemas";
    }

    @GetMapping(value = "/admin/editar/problema/{id}")
    public ModelAndView updateProblema(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        modelAndView.addObject("usuario2", user);
        Problema problema = problemaService.findById(id);
        modelAndView.addObject("problema", problema);
        modelAndView.setViewName("problema/atualizar-problema");
        return modelAndView;
    }

    @PostMapping(value = "/admin/editar/problema")
    public String editSave(@ModelAttribute Problema problema, RedirectAttributes redirectAttributes){
        problemaService.insert(problema);
        redirectAttributes.addAttribute("msg", "Problema atualizado com sucesso");
        return "redirect:/admin/listar/problemas";
    }




}
