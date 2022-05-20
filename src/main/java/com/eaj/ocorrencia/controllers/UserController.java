package com.eaj.ocorrencia.controllers;

import com.eaj.ocorrencia.models.Role;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.RoleRepository;
import com.eaj.ocorrencia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


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
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("usuario", user);
            modelAndView.setViewName("registerUser");
            return modelAndView;
        }
        try {
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

        }catch (Exception e){
            modelAndView.addObject("erroNumero", "Já existe uma moto com este número");
            modelAndView.addObject("usuario", user);
            modelAndView.setViewName("registerUser");
        }

        return modelAndView;
    }





}
