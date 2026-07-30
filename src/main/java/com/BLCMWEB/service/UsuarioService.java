/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

/**
 *
 * @author peper
 */

import com.BLCMWEB.domain.UsuarioListadoDTO;
import com.BLCMWEB.domain.Usuarios;
import com.BLCMWEB.repository.UsuarioRepository;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void insertarUsuario(Usuarios usuario) {
        usuarioRepository.insertarUsuario(usuario);
    }

    public void actualizarUsuario(Usuarios usuario) {
        usuarioRepository.actualizarUsuario(usuario);
    }
    
    public List<UsuarioListadoDTO> readAllUsuario() {
        return usuarioRepository.readAllUsuario();
    }

    public void eliminarUsuario(Integer cedula) {
        Usuarios usuario = new Usuarios();
        usuario.setCedula(cedula);
        usuarioRepository.deleteUsuario(usuario);
    }
    
    public UsuarioListadoDTO buscarPorId(Integer id) {
        return readAllUsuario().stream()
                .filter(user -> user.getCedula().equals(id))
                .findFirst()
                .orElse(null);
    }
    
        public void cambiarPassword(Integer cedula, String passwordHash) {
        usuarioRepository.cambiarClave(cedula, passwordHash);
    }
    
}

