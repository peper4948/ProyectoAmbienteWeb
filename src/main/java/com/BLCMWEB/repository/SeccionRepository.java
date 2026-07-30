/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.repository;

import com.BLCMWEB.domain.Seccion;
import com.BLCMWEB.domain.SeccionListadoDTO;
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
public class SeccionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall seccionInsertCall;
    private SimpleJdbcCall seccionUpdateCall;
    private SimpleJdbcCall seccionDeleteCall;
    private SimpleJdbcCall seccionReadAllCall;
    private SimpleJdbcCall seccionPorBandaCall;

    @PostConstruct
    public void init() {
        seccionInsertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_SECCION_INSERT_SP");

        seccionUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_SECCION_UPDATE_SP");

        seccionDeleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_SECCION_DELETE_LOGICO_SP");

        seccionReadAllCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_SECCION_LISTAR_SP")
                .returningResultSet("p_cursor", 
                        BeanPropertyRowMapper.newInstance(SeccionListadoDTO.class));
    }

    public void insertarSeccion(Seccion seccion) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_SECCION_NOMBRE", seccion.getNombre());
        params.put("P_SECCION_DESCRIPCION", seccion.getDescripcion());
        params.put("P_SECCION_ID_ESTADO", seccion.getIdEstado());
        
        seccionInsertCall.execute(params);
    }

    public void actualizarSeccion(Seccion seccion) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_SECCION_ID_SECCION", seccion.getIdSeccion());
        params.put("P_SECCION_NOMBRE", seccion.getNombre());
        params.put("P_SECCION_DESCRIPCION", seccion.getDescripcion());
        params.put("P_SECCION_ID_ESTADO", seccion.getIdEstado());
        seccionUpdateCall.execute(params);
    }

    public 
        List<SeccionListadoDTO> readAllSeccion() {
        Map<String, Object> result = seccionReadAllCall.execute();
        return (List<SeccionListadoDTO>) result.get("p_cursor");
    }

    public void deleteSeccion(Seccion seccion) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_SECCION_ID_SECCION", seccion.getIdSeccion());
        seccionDeleteCall.execute(params);
    }


}
