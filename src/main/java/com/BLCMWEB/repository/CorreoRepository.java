/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.repository;

import com.BLCMWEB.domain.Correo;
import com.BLCMWEB.domain.CorreoListadoDTO;
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
public class CorreoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall correoInsertCall;
    private SimpleJdbcCall correoUpdateCall;
    private SimpleJdbcCall correoDeleteCall;
    private SimpleJdbcCall correoReadAllCall;

    @PostConstruct
    public void init() {
        correoInsertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_CORREO_INSERT_SP");

        correoUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_CORREO_UPDATE_SP");

        correoDeleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_CORREO_DELETE_SP");

        correoReadAllCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_CORREO_LISTAR_SP")
                .returningResultSet("p_cursor",
                BeanPropertyRowMapper.newInstance(CorreoListadoDTO.class));;
    }

public void insertarCorreo(Correo correo) {
    Map<String, Object> params = new HashMap<>();
    // Usamos las llaves exactas que espera el nuevo procedimiento
    params.put("P_CORREO_EMAIL", correo.getCorreo()); // Asegúrate de que el DTO o entidad devuelva el string aquí
    params.put("P_CORREO_ID_ESTADO", correo.getIdEstado() != null ? correo.getIdEstado() : 1);
    
    // Ejecutamos la llamada
    Map<String, Object> result = correoInsertCall.execute(params);
    
    // Capturamos el ID autogenerado que devuelve Oracle
    if (result.get("P_CORREO_ID_OUT") != null) {
        Number idGenerado = (Number) result.get("P_CORREO_ID_OUT");
        correo.setIdCorreo(idGenerado.intValue());
    }
}

public void actualizarCorreo(Correo correo) {
    Map<String, Object> params = new HashMap<>();
    params.put("P_CORREO_ID_CORREO", correo.getIdCorreo());
    params.put("P_CORREO_EMAIL", correo.getCorreo());
    params.put("P_CORREO_ID_ESTADO", correo.getIdEstado());
    correoUpdateCall.execute(params);
}
public List<CorreoListadoDTO> readAllCorreo() {
    Map<String, Object> result = correoReadAllCall.execute();
    return (List<CorreoListadoDTO>) result.get("p_cursor");
}

public void deleteCorreo(Correo correo) {
    Map<String, Object> params = new HashMap<>();
    params.put("P_CORREO_ID_CORREO", correo.getIdCorreo());
    correoDeleteCall.execute(params);
}
}