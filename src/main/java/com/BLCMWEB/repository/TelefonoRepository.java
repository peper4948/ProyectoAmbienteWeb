/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.repository;

import com.BLCMWEB.domain.Telefono;
import com.BLCMWEB.domain.TelefonoListadoDTO;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author peper
 */
@Repository
public class TelefonoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall telefonoInsertCall;
    private SimpleJdbcCall telefonoUpdateCall;
    private SimpleJdbcCall telefonoDeleteCall;
    private SimpleJdbcCall telefonoReadAllCall;

    @PostConstruct
    public void init() {
        telefonoInsertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_TELEFONO_INSERT_SP");

        telefonoUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_TELEFONO_UPDATE_SP");

        telefonoDeleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_TELEFONO_DELETE_SP");

        telefonoReadAllCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_TELEFONO_LISTAR_SP")
                .returningResultSet("p_cursor",
                BeanPropertyRowMapper.newInstance(TelefonoListadoDTO.class));;
    }

public void insertarTelefono(Telefono telefono) {
    Map<String, Object> params = new HashMap<>();
    params.put("P_TEL_TELEFONO", telefono.getTelefono()); 
    params.put("P_TEL_ID_ESTADO", telefono.getIdEstado() != null ? telefono.getIdEstado() : 1);
    
    // Ejecutamos la llamada
    Map<String, Object> result = telefonoInsertCall.execute(params);
    
    // Capturamos el ID autogenerado que devuelve Oracle
    if (result.get("P_TEL_ID_OUT") != null) {
        Number idGenerado = (Number) result.get("P_TEL_ID_OUT");
        telefono.setIdTelefono(idGenerado.intValue());
    }
}
public void actualizarTelefono(Telefono telefono) {
    Map<String, Object> params = new HashMap<>();
    params.put("P_TEL_ID_TELEFONO", telefono.getIdTelefono());
    params.put("P_TEL_TELEFONO", telefono.getTelefono());
    params.put("P_TEL_ID_ESTADO", telefono.getIdEstado());
    telefonoUpdateCall.execute(params);
}

    public 
        List<TelefonoListadoDTO> readAllTelefono() {
        Map<String, Object> result = telefonoReadAllCall.execute();
        return (List<TelefonoListadoDTO>) result.get("p_cursor");
    }

    public void deleteTelefono(Telefono telefono) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_ID_TELEFONO", telefono.getIdTelefono());
        telefonoDeleteCall.execute(params);
    }
}