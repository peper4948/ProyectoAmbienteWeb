/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

import com.BLCMWEB.domain.Estado;
import com.BLCMWEB.domain.EstadoListadoDTO;
import com.BLCMWEB.repository.EstadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author peper
 */
@Service
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public void insertarEstado(Estado estado) {
        estadoRepository.insertarEstado(estado);
    }

    public void actualizarEstado(Estado estado) {
        estadoRepository.actualizarEstado(estado);
    }
    
    public List<EstadoListadoDTO> readAllEstado() {
        return estadoRepository.readAllEstado();
    }

    public void eliminarEstado(Integer idEstado) {
        Estado estado = new Estado();
        estado.setIdEstado(idEstado);
        estadoRepository.deleteEstado(estado);
    }
    
    public EstadoListadoDTO buscarPorId(Integer id) {
        return readAllEstado().stream()
                .filter(asis -> asis.getIdEstado().equals(id))
                .findFirst()
                .orElse(null);
    }
    
}
