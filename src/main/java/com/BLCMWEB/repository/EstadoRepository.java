/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.repository;

import com.BLCMWEB.domain.Estado;
import com.BLCMWEB.domain.EstadoListadoDTO;
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
public class EstadoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall estadoInsertCall;
    private SimpleJdbcCall estadoUpdateCall;
    private SimpleJdbcCall estadoDeleteCall;
    private SimpleJdbcCall estadoReadAllCall;

@PostConstruct
    public void init() {
        estadoInsertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_ESTADO_INSERT_SP");

        estadoUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_ESTADO_UPDATE_SP");

        estadoDeleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_ESTADO_DELETE_SP");

        // Usamos un RowMapper manual para emparejar los nombres reales de las columnas del SP con el DTO
        estadoReadAllCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_ESTADO_LISTAR_SP")
                .returningResultSet("p_cursor", (rs, rowNum) -> {
                    EstadoListadoDTO dto = new EstadoListadoDTO();
                    dto.setIdEstado(rs.getInt(1));    // Columna 1: ID_ESTADO
                    dto.setEstado(rs.getString(2));  // Columna 2: NOMBRE_ESTADO
                    return dto;
                });
    }

    public void insertarEstado(Estado estado) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_ESTADO_NOMBRE_ESTADO", estado.getEstado());
        estadoInsertCall.execute(params);
    }

    public void actualizarEstado(Estado estado) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_ESTADO_ID_ESTADO", estado.getIdEstado());
        params.put("P_ESTADO_NOMBRE_ESTADO", estado.getEstado());
        estadoUpdateCall.execute(params);
    }

    public void deleteEstado(Estado estado) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_ESTADO_ID_ESTADO", estado.getIdEstado());
        estadoDeleteCall.execute(params);
    }

    public List<EstadoListadoDTO> readAllEstado() {
        Map<String, Object> result = estadoReadAllCall.execute();
        return (List<EstadoListadoDTO>) result.get("p_cursor");
    }

}