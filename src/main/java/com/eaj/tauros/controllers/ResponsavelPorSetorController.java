package com.eaj.tauros.controllers;


import com.eaj.tauros.models.Chamado;
import com.eaj.tauros.models.Setor;
import com.eaj.tauros.models.User;
import com.eaj.tauros.services.ChamadoService;
import com.eaj.tauros.services.SetorService;
import com.eaj.tauros.services.UserService;
import com.eaj.tauros.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ResponsavelPorSetorController {

    @Autowired
    SetorService setorService;

    @Autowired
    UserService userService;

    @Autowired
    ChamadoService chamadoService;

    @GetMapping("/listar/chamados-responsavel-setor")
    public ModelAndView chamadosAdminIndex(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        Setor setor = user.getSetor();
        modelAndView.addObject("usuario2", user);
        Integer totalConluidos = chamadoService.totalChamadosPorSetorEStatus(Constantes.STATUS_CONCLUIDO,setor);
        Integer totalEmAndamento = chamadoService.totalChamadosPorSetorEStatus(Constantes.STATUS_ANDAMENTO,setor);
        Integer totalEmAtraso = chamadoService.totalChamadosPorSetorEStatus(Constantes.STATUS_ATRASADO,setor);
        Integer totalAbertos = chamadoService.totalChamadosPorSetorEStatus(Constantes.STATUS_ABERTO,setor);
        List<Chamado> chamadosEmAndamento = chamadoService.findByStatusAndSetor(Constantes.STATUS_ANDAMENTO,setor);
        List<Chamado> chamadosEmAberto = chamadoService.findByStatusAndSetor(Constantes.STATUS_ABERTO,setor);
        modelAndView.addObject("chamadosEmAndamento", chamadosEmAndamento);
        getModAndViewsForChamados(modelAndView, totalConluidos, totalEmAndamento, totalEmAtraso, totalAbertos, chamadosEmAberto, "chamadosEmAberto");
        modelAndView.setViewName("indexResponsavelSetor");
        return modelAndView;
    }

    private void getModAndViewsForChamados(ModelAndView modelAndView, Integer totalConluidos, Integer totalEmAndamento, Integer totalEmAtraso, Integer totalAbertos, List<Chamado> chamadosEmAberto, String chamadosEmAberto2) {
        modelAndView.addObject(chamadosEmAberto2, chamadosEmAberto);
        modelAndView.addObject("concluidos", totalConluidos);
        modelAndView.addObject("emAndamento", totalEmAndamento);
        modelAndView.addObject("totalAbertos", totalAbertos);
        modelAndView.addObject("totalEmAtraso", totalEmAtraso);
    }
}
