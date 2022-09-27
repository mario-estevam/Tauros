package com.eaj.ocorrencia.services;

import com.eaj.ocorrencia.models.Role;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.RoleRepository;
import com.eaj.ocorrencia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Boolean findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user ==null){
            return true;
        }else{
            return false;
        }
    }

    public User findById(Long id){
        return userRepository.getById(id);
    }

    public Boolean findUserUsernameBoolean(String userName){
        User user = userRepository.findByUserName(userName);
        if(user ==null){
            return true;
        }else{
            return false;
        }
    }

    public List<User> getAll(){
        return userRepository.findAllByActiveTrue();
    }


    public List<User> getAllPendentes(){
        return userRepository.findAllByActiveFalse();
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Boolean confirmarSenha(String senha, String repetirSenha){
        if(senha.equals(repetirSenha)){
            return true;
        }else{
            return false;
        }
    }

    public User saveUser(User user) {
        user.setSenha(bCryptPasswordEncoder.encode(user.getSenha()));
        user.setRepetirSenha(bCryptPasswordEncoder.encode(user.getRepetirSenha()));
        Role userRole = user.getRole();
        user.setActive(true);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public User saveUserPublic(User user) {
        user.setSenha(bCryptPasswordEncoder.encode(user.getSenha()));
        user.setRepetirSenha(bCryptPasswordEncoder.encode(user.getRepetirSenha()));
        Role userRole = user.getRole();
        user.setActive(Boolean.FALSE);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        return userRepository.save(user);
    }

    public void ativarUsuario(Long id) {
        User user = userRepository.getById(id);
        user.setActive(true);
        userRepository.save(user);
    }

    public void deletar(Long id) {
        User user = userRepository.getById(id);
        user.setActive(false);
        userRepository.save(user);
    }
}
