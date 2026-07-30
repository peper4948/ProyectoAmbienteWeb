/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

import com.BLCMWEB.domain.Correo;
import com.BLCMWEB.domain.CorreoListadoDTO;
import com.BLCMWEB.repository.CorreoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author peper
 */
@Service
public class CorreoService {
    @Autowired
    private CorreoRepository correoRepository;

    public void insertarCorreo(Correo correo) {
        correoRepository.insertarCorreo(correo);
    }

    public void actualizarCorreo(Correo correo) {
        correoRepository.actualizarCorreo(correo);
    }
    
    public List<CorreoListadoDTO> readAllCorreo() {
        return correoRepository.readAllCorreo();
    }

    public void eliminarCorreo(Integer idCorreo) {
        Correo correo = new Correo();
        correo.setIdCorreo(idCorreo);
        correoRepository.deleteCorreo(correo);
    }
    
    public CorreoListadoDTO buscarPorId(Integer id) {
        return readAllCorreo().stream()
                .filter(correo -> correo.getIdCorreo().equals(id))
                .findFirst()
                .orElse(null);
    }
    
}

