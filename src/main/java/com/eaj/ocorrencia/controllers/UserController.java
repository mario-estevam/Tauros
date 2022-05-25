package com.eaj.ocorrencia.controllers;

import com.eaj.ocorrencia.models.Role;
import com.eaj.ocorrencia.models.Setor;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.RoleRepository;
import com.eaj.ocorrencia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value={"/index"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        if(user != null){
            modelAndView.addObject("usuario", user);
            System.out.println(user.getRole().getRole());
            modelAndView.setViewName("index");

        }else{
            modelAndView.addObject("userName", "Nenhum usuário logado no sistema");
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }


    @GetMapping(value="/registrar")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("usuario", user);
        modelAndView.setViewName("registerUser");
        return modelAndView;
    }

    @PostMapping(value = "/registrar")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

            Boolean confirm = userService.confirmarSenha(user.getSenha(),user.getRepetirSenha());
            Boolean emailConfirm = userService.findUserByEmail(user.getEmail());
            Boolean userNameConfirm = userService.findUserUsernameBoolean(user.getUserName());
            if(!confirm){
                modelAndView.addObject("senhas","as senhas não coincidem");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("registerUser");
            } else if(!userNameConfirm){
                modelAndView.addObject("userName","Este nome de usuário já existe");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("registerUser");
            } else if(!emailConfirm){
                modelAndView.addObject("email","Este email já foi cadastrado");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("registerUser");
            } else {
                userService.saveUser(user);
                modelAndView.addObject("successMessage", "Usuario cadastrado com sucesso");
                modelAndView.addObject("usuario", new User());
                modelAndView.setViewName("registerUser");
            }

        return modelAndView;
    }


    @GetMapping(value="/admin/cadastro/usuario")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);
        User user = new User();
        modelAndView.addObject("usuario", user);
        modelAndView.setViewName("cadastro-usuario");
        return modelAndView;
    }

    @PostMapping(value = "/admin/cadastro/usuario")
    public ModelAndView postForUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);

            Boolean confirm = userService.confirmarSenha(user.getSenha(),user.getRepetirSenha());
            Boolean emailConfirm = userService.findUserByEmail(user.getEmail());
            Boolean userNameConfirm = userService.findUserUsernameBoolean(user.getUserName());
            if(!confirm){
                modelAndView.addObject("senhas","as senhas não coincidem");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("cadastro-usuario");
            } else if(!userNameConfirm){
                modelAndView.addObject("userName","Este nome de usuário já existe");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("cadastro-usuario");
            } else if(!emailConfirm){
                modelAndView.addObject("email","Este email já foi cadastrado");
                modelAndView.addObject("usuario", user);
                modelAndView.setViewName("cadastro-usuario");
            } else {
                userService.saveUser(user);
                modelAndView.addObject("successMessage", "Usuario atualizado com sucesso");
                modelAndView.addObject("usuario", new User());
                modelAndView.setViewName("cadastro-usuario");
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

        modelAndView.setViewName("usuarios");
        return modelAndView;
    }

    @GetMapping(value = "/admin/editar/usuario/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);
        User user = userService.findById(id);
        modelAndView.addObject("usuario", user);
        modelAndView.setViewName("atualizar-usuario");
        return modelAndView;
    }

    @PostMapping(value = "/admin/editar/usuario")
    public ModelAndView editSave(User user){

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user2 = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("usuario2", user2);
        Boolean confirm = userService.confirmarSenha(user.getSenha(),user.getRepetirSenha());

        if(!confirm){
            modelAndView.addObject("senhas","as senhas não coincidem");
            modelAndView.addObject("usuario", user);
            modelAndView.setViewName("atualizar-usuario");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Usuario atualizado com sucesso");
            modelAndView.addObject("usuario", new User());
            modelAndView.setViewName("atualizar-usuario");
        }

        return modelAndView;
    }




}
