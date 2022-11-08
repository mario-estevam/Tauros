package com.eaj.tauros.services;

import com.eaj.tauros.models.Role;
import com.eaj.tauros.models.Setor;
import com.eaj.tauros.models.User;
import com.eaj.tauros.repositories.RoleRepository;
import com.eaj.tauros.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
        return userRepository.findAllByActiveTrueAndDeleteIsNull();
    }

    public List<User> getAllBySetor(Setor setor){
        return userRepository.findAllByActiveTrueAndDeleteIsNullAndSetor(setor);
    }


    public List<User> getAllInactives(){
        return userRepository.findAllByDeleteIsNotNull();
    }

    public List<User> getAllInactivesBySetor(Setor setor){
        return userRepository.findAllByDeleteIsNotNullAndSetor(setor);
    }


    public List<User> getAllPendentes(){
        return userRepository.findAllByActiveFalseAndDeleteIsNull();
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

    public User updateUser(User user) {
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

    public Integer countAllPendentes(){
        return userRepository.countAllByActiveFalseAndDeleteIsNull();
    }

    public void ativarUsuario(Long id) {
        User user = userRepository.getById(id);
        user.setActive(true);
        userRepository.save(user);
    }

    public void ativarUsuarioInativo(Long id) {
        User user = userRepository.getById(id);
        user.setActive(true);
        user.setDelete(null);
        userRepository.save(user);
    }

    public void deletar(Long id) {
        User user = userRepository.getById(id);
        user.setActive(false);
        LocalDate localDate = LocalDate.now();
        user.setDelete(localDate);
        userRepository.save(user);
    }

    public void deletarPermanente(Long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }
}
