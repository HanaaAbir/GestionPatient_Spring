package com.example.GestionPatientMVC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration // classe de config
@EnableWebSecurity // activer securite web
@EnableMethodSecurity(prePostEnabled = true) // gérer securité
public class SecurityConfig {

    @Bean // methode se lance au demarrage
    public InMemoryUserDetailsManager userDetailsService() {

        PasswordEncoder passwordEncoder = passwordEncoder(); // injection de dependance de la methode de config passwordEncoder
        String encodedPassword = passwordEncoder.encode("1234"); // encoder le mot de passe
        // Create une liste ou on va stocker plusieurs utilisateurs
        List<UserDetails> users = new ArrayList<>();

        // Ajouter premier utilisateur
        UserDetails user1 = User.builder()
                .username("user1")
                .password(encodedPassword)
                .roles("USER")
                .build();
        users.add(user1);

        // Ajouter second utilisateur
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMIN", "USER")
                .build();
        users.add(user2);

        // Ajouter troisieme utilisateur
        UserDetails user3 = User.builder()
                .username("user3")
                .password(passwordEncoder.encode("1234"))
                .roles("USER", "ADMIN")
                .build();
        users.add(user3);

        // retourner InMemoryUserDetailsManager(users) avec la liste des utilisateurs
        return new InMemoryUserDetailsManager(users);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin();
        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER") ;
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN") ;
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/noAuthorized") ;
        return httpSecurity.build() ;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
