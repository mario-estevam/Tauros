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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/meus-chamados")
    public ModelAndView chamadosRequisitante(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<Chamado> chamados = chamadoService.meusChamados(user);
        modelAndView.addObject("chamados", chamados);

        if(user != null){
            modelAndView.addObject("usuario2", user);
            System.out.println(user.getRole().getRole());
            modelAndView.setViewName("index");

        }else{
            modelAndView.addObject("userName", "Nenhum usuário logado no sistema");
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @GetMapping("/listar/chamados-admin")
    public ModelAndView chamadosAdminIndex(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam(value = "status", defaultValue = "ANDAMENTO") String status){
        ModelAndView modelAndView = new ModelAndView();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        modelAndView.addObject("status", status);
        Page<Chamado> chamadosAbertos = chamadoService.findPaginated(PageRequest.of(currentPage - 1, pageSize),  status);
        model.addAttribute("chamadosAbertos", chamadosAbertos);
        int totalPagesPendentes =  chamadosAbertos.getTotalPages();
        if (totalPagesPendentes > 0) {
            List<Integer> pageNumbersPendente = IntStream.rangeClosed(1, totalPagesPendentes)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbersPendente);

        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);
        Integer totalConluidos = chamadoService.totalConcluidos();
        Integer totalEmAndamento = chamadoService.totalEmAndamento();
        Integer totalEmAtraso = chamadoService.totalEmAtraso();
        Integer totalAbertos = chamadoService.totalAbertos();
        modelAndView.addObject("concluidos", totalConluidos);
        modelAndView.addObject("emAndamento", totalEmAndamento);
        modelAndView.addObject("totalAbertos",totalAbertos);
        modelAndView.addObject("totalEmAtraso", totalEmAtraso);
        modelAndView.setViewName("indexAdmin");
        return modelAndView;
    }


    @GetMapping(value = "/chamados")
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


    @GetMapping(value = "/admin/chamados/meus-atendimentos")
    public ModelAndView meusAtendimentos(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user);

        List<Chamado> chamados = chamadoService.meusAtendimentos(user);
        modelAndView.addObject("chamados", chamados);


        modelAndView.setViewName("chamado/atendimentos");
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


    @RequestMapping("/admin/finalizar-atendimento/{id}")
    public String finalizarAtendimento(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes){
        Chamado chamado = chamadoService.findById(id);
        chamadoService.finalizar(chamado);
        return "redirect:/admin/chamados/meus-atendimentos";
    }



    @GetMapping(value = "/detalhes/chamado/{id}")
    public ModelAndView detalhesChamado(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        modelAndView.addObject("usuario2", user);

        Chamado chamado = chamadoService.findById(id);
        modelAndView.addObject("chamado", chamado);

        modelAndView.setViewName("chamado/detalhes-chamado");
        return modelAndView;
    }


    @RequestMapping("/deletar/chamado/{id}")
    public String doDelete(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes){
        chamadoService.delete(id);
        redirectAttributes.addAttribute("msg", "Deletado com sucesso");
        return "redirect:/index";
    }


}
