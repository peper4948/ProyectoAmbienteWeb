/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

/**
 *
 * @author peper
 */

import com.BLCMWEB.domain.Telefono;
import com.BLCMWEB.domain.TelefonoListadoDTO;
import com.BLCMWEB.repository.TelefonoRepository;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;


@Service
public class TelefonoService {
    @Autowired
    private TelefonoRepository telefonoRepository;

    public void insertarTelefono(Telefono telefono) {
        telefonoRepository.insertarTelefono(telefono);
    }

    public void actualizarTelefono(Telefono telefono) {
        telefonoRepository.actualizarTelefono(telefono);
    }
    
    public List<TelefonoListadoDTO> readAllTelefono() {
        return telefonoRepository.readAllTelefono();
    }

    public void eliminarTelefono(Integer idTelefono) {
        Telefono telefono = new Telefono();
        telefono.setIdTelefono(idTelefono);
        telefonoRepository.deleteTelefono(telefono);
    }
    
    public TelefonoListadoDTO buscarPorId(Integer id) {
        return readAllTelefono().stream()
                .filter(tel -> tel.getIdTelefono().equals(id))
                .findFirst()
                .orElse(null);
    }
    
}