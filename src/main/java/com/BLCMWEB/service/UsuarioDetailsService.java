/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

import com.BLCMWEB.domain.UsuarioLoginDTO;
import com.BLCMWEB.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author peper
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

@Autowired
private UsuarioRepository usuarioRepository;

@Autowired
private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder; // <--- Agrega esto
    

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        System.out.println(">>> loadUserByUsername: [" + correo + "]");

        UsuarioLoginDTO usuario = usuarioRepository.buscarPorCorreo(correo);

        System.out.println(">>> encontrado: " + (usuario != null));

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + correo);
        }
        if (usuario.getClave() == null || usuario.getClave().isBlank()) {
            throw new UsernameNotFoundException("El usuario no tiene contraseña configurada");
        }

        List<String> roles = usuarioRepository.buscarRolesPorCedula(usuario.getCedula());
        System.out.println(">>> roles: " + roles);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String rol : roles) {
            authorities.add(new SimpleGrantedAuthority(
                "ROLE_" + rol.trim().toUpperCase().replace(" ", "_").replace("-", "_")
            ));
        }

        boolean activo = usuario.getIdEstado() != null && usuario.getIdEstado() == 1;

        
        System.out.println(">>> COPIA ESTE HASH EN TU BASE DE DATOS: " + passwordEncoder.encode("1234"));
        
        return User.withUsername(correo) // <--- Usa el parámetro del método directamente
                .password(usuario.getClave())
                .authorities(authorities)
                .disabled(!activo)
                .build();
    }
}
