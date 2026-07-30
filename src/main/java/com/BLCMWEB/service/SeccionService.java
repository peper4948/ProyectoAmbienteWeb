/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

import com.BLCMWEB.domain.Seccion;
import com.BLCMWEB.domain.SeccionListadoDTO;
import com.BLCMWEB.repository.SeccionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author peper
 */
@Service
public class SeccionService {
    @Autowired
    private SeccionRepository seccionRepository;

       
    public void insertarSeccion(Seccion seccion) {
        seccionRepository.insertarSeccion(seccion);
    }

    public void actualizarSeccion(Seccion seccion) {
        seccionRepository.actualizarSeccion(seccion);
    }
    
    public List<SeccionListadoDTO> readAllSeccion() {
        return seccionRepository.readAllSeccion();
    }

    public void eliminarSeccion(Integer idSeccion) {
        Seccion seccion = new Seccion();
        seccion.setIdSeccion(idSeccion);
        seccionRepository.deleteSeccion(seccion);
    }
    
    public SeccionListadoDTO buscarPorId(Integer id) {
        return readAllSeccion().stream()
                .filter(sec -> sec.getIdSeccion().equals(id))
                .findFirst()
                .orElse(null);
    }
}
