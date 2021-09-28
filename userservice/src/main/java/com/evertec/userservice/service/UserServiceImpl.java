package com.evertec.userservice.service;

import com.evertec.userservice.domain.Role;
import com.evertec.userservice.domain.User;
import com.evertec.userservice.repo.RoleRepo;
import com.evertec.userservice.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger log= LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(userName);
        if (user == null){
            log.error("Usuario no encontrado en la base de datos");
            throw new UsernameNotFoundException("Usuario no encontrado en la base de datos");
        }else{
            log.info("Usuario encontrado en la base de datos: {}", userName);
        }

        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo,PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        log.info("Guardando un nuevo usuario {} en la base de datos",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Guardando un nuevo role {} en la base de datos",role.getName());
        return this.roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Agregando role {} al usuario {}", roleName, username);
        User user=this.userRepo.findByUsername(username);
        Role role=this.roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Recuperando usuario {}",username);
        return this.userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Recuperando todos los usuarios");
        return this.userRepo.findAll();
    }
}
