package com.eaj.tauros.controllers;


import com.eaj.tauros.dtos.OperadorDTO;
import com.eaj.tauros.dtos.RelatorioSetorDTO;
import com.eaj.tauros.models.Problema;
import com.eaj.tauros.models.Setor;
import com.eaj.tauros.models.User;
import com.eaj.tauros.services.RelatoriosService;
import com.eaj.tauros.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RelatorioController {

    @Autowired
    private final RelatoriosService relatoriosService;

    private final UserService userService;

    public RelatorioController(RelatoriosService relatoriosService, UserService userService) {
        this.relatoriosService = relatoriosService;
        this.userService = userService;
    }

    @GetMapping(value = "/relatorio/setor/responsavel")
    public ModelAndView relatorioResponsavel(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        Integer count = userService.countAllPendentes();
        modelAndView.addObject("count", count);
        modelAndView.addObject("usuario2", user);
        RelatorioSetorDTO relatorio = relatoriosService.relatorioSetorResponsavel(user.getSetor());
        List<OperadorDTO> operadores = relatorio.getOperadores();
        List<Problema> problemas = relatorio.getProblemas();
        modelAndView.addObject("operadores", operadores);
        modelAndView.addObject("problemas", problemas);
        modelAndView.setViewName("relatorios/relatorio-setor-responsavel");
        return modelAndView;
    }
}
