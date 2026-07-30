/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

import com.BLCMWEB.domain.Direccion;
import com.BLCMWEB.domain.DireccionListadoDTO;
import com.BLCMWEB.repository.DireccionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author peper
 */
@Service
public class DireccionService {
    @Autowired
    private DireccionRepository direccionRepository;

    public void insertarDireccion(Direccion direccion) {
        direccionRepository.insertarDireccion(direccion);
    }

    public void actualizarDireccion(Direccion direccion) {
        direccionRepository.actualizarDireccion(direccion);
    }
    
    public List<DireccionListadoDTO> readAllDireccion() {
        return direccionRepository.readAllDireccion();
    }

    public void eliminarDireccion(Integer idDireccion) {
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(idDireccion);
        direccionRepository.deleteDireccion(direccion);
    }
    
    public DireccionListadoDTO buscarPorId(Integer idDireccion) {
        return readAllDireccion().stream()
                .filter(dire -> dire.getIdDireccion().equals(idDireccion))
                .findFirst()
                .orElse(null);
    }
    
}

