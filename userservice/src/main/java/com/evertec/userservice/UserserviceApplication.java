package com.evertec.userservice;

import com.evertec.userservice.domain.Role;
import com.evertec.userservice.domain.User;
import com.evertec.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role(null,"ROLE_USER"));
            userService.saveRole(new Role(null,"ROLE_ADMIN"));
            userService.saveRole(new Role(null,"ROLE_COMERCIAL"));

            userService.saveUser(new User(null,"evertecadmin", "admin","1234", new ArrayList<>()));
            userService.saveUser(new User(null,"evertecuser", "user","1234", new ArrayList<>()));
            userService.saveUser(new User(null,"everteccomercial", "comercial","1234", new ArrayList<>()));

            userService.addRoleToUser("admin","ROLE_ADMIN");
            userService.addRoleToUser("user","ROLE_USER");
            userService.addRoleToUser("comercial","ROLE_COMERCIAL");
        };
    }
}
