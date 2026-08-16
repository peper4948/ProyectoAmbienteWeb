package com.BLCMWEB.service;

import com.BLCMWEB.domain.UsuarioListadoDTO;
import com.BLCMWEB.domain.UsuarioLoginDTO;
import com.BLCMWEB.domain.Usuarios;
import com.BLCMWEB.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void asignarRol(Integer cedula, Integer idRol) {
        usuarioRepository.asignarRol(cedula, idRol);
    }
    public UsuarioLoginDTO buscarPorCorreo(String correo) {
        return usuarioRepository.buscarPorCorreo(correo);
    }
}
