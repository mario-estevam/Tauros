package com.eaj.ocorrencia.controllers;

import com.eaj.ocorrencia.models.Setor;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.RoleRepository;
import com.eaj.ocorrencia.services.ChamadoService;
import com.eaj.ocorrencia.services.SetorService;
import com.eaj.ocorrencia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SetorService setorService;

    @Autowired
    ChamadoService chamadoService;


    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value={"/index"})
    public String index(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
      if(Objects.equals(user.getRole().getRole(), "ADMIN")){
            return "redirect:/listar/chamados-admin";
        } else if(Objects.equals(user.getRole().getRole(), "REQUISITANTE")){
          return "redirect:/meus-chamados";
        } else if(Objects.equals(user.getRole().getRole(), "OPERADOR")){
          return "redirect:/listar/chamados-operador";
        }else{
          return "redirect:/login";
        }
    }

    @GetMapping(value={"/voltar"})
    public String voltar(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        if(Objects.equals(user.getRole().getRole(), "ADMIN")){
            return "redirect:/listar/chamados-admin";
        } else if(Objects.equals(user.getRole().getRole(), "REQUISITANTE")){
            return "redirect:/meus-chamados";
        } else if(Objects.equals(user.getRole().getRole(), "OPERADOR")){
            return "redirect:/listar/chamados-operador";
        }else{
            return "redirect:/login";
        }
    }


    @GetMapping(value="/admin/cadastro/usuario")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);
        User user = new User();
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        modelAndView.addObject("usuario", user);
        modelAndView.setViewName("usuario/cadastro-usuario");
        return modelAndView;
    }

    @PostMapping(value = "/admin/cadastro/usuario")
    public ModelAndView postForUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        modelAndView.addObject("usuario2", user2);

            Boolean confirm = userService.confirmarSenha(user.getSenha(),user.getRepetirSenha());
            Boolean emailConfirm = userService.findUserByEmail(user.getEmail());
            Boolean userNameConfirm = userService.findUserUsernameBoolean(user.getUserName());
            if(!confirm){
                modelAndView.addObject("senhas","as senhas não coincidem");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("usuario/cadastro-usuario");
            } else if(!userNameConfirm){
                modelAndView.addObject("userName","Este nome de usuário já existe");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("usuario/cadastro-usuario");
            } else if(!emailConfirm){
                modelAndView.addObject("email","Este email já foi cadastrado");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("usuario/cadastro-usuario");
            } else {
                userService.saveUser(user);
                modelAndView.addObject("successMessage", "Usuario atualizado com sucesso");
                modelAndView.addObject("usuario", new User());
                modelAndView.setViewName("usuario/cadastro-usuario");
            }


        return modelAndView;
    }


    @GetMapping(value = "/admin/listar/usuarios")
    public ModelAndView listUsuarios(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);
        List<User> userList = userService.getAll();
        modelAndView.addObject("usuarios", userList);

        modelAndView.setViewName("usuario/usuarios");
        return modelAndView;
    }

    @GetMapping(value = "/admin/editar/usuario/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);
        User user = userService.findById(id);
        modelAndView.addObject("usuario", user);
        modelAndView.setViewName("usuario/atualizar-usuario");
        return modelAndView;
    }


    @GetMapping(value = "/editar/usuario")
    public ModelAndView updateUser(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        modelAndView.addObject("usuario2", user2);
        modelAndView.addObject("usuario", user2);
        modelAndView.setViewName("usuario/atualizar-usuario");
        return modelAndView;
    }

    @PostMapping(value = "/editar/usuario")
    public ModelAndView editSaver(User user){

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        List<Setor> setores = setorService.getAll();
        modelAndView.addObject("setores", setores);
        modelAndView.addObject("usuario2", user2);
        modelAndView.addObject("usuario", user2);
        Boolean confirm = userService.confirmarSenha(user.getSenha(),user.getRepetirSenha());

        if(Boolean.FALSE.equals(confirm)){
            modelAndView.addObject("senhas","as senhas não coincidem");
            modelAndView.addObject("usuario", user);
            modelAndView.setViewName("usuario/atualizar-usuario");
        } else if(user2.getRole().getRole().equals("ADMIN")) {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Usuario atualizado com sucesso");
            modelAndView.addObject("usuario", new User());
            modelAndView.setViewName("usuario/atualizar-usuario");
        }else{
            modelAndView.addObject("successMessage", "Usuario atualizado com sucesso");
            modelAndView.addObject("usuario",user2);
            modelAndView.setViewName("usuario/atualizar-usuario");
        }

        if(user.getSenha()==null){
            modelAndView.addObject("senhas","insira uma senha");
            modelAndView.addObject("usuario", user);
            modelAndView.setViewName("usuario/atualizar-usuario");
        }

        return modelAndView;
    }

}
