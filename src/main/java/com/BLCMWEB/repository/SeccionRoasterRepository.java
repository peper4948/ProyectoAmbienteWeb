/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.repository;

import com.BLCMWEB.domain.IntegrantesSeccionDTO;
import com.BLCMWEB.controller.IntegranteSeccionRowMapper;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
 
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;
 
@Repository
public class SeccionRoasterRepository {
 
    private static final String PACKAGE = "BLCM_PROYECTO_PCK";  // ajustá si tu package tiene otro nombre
 
    private final SimpleJdbcCall listarPorSeccionCall;
 
    public SeccionRoasterRepository(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 
        this.listarPorSeccionCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_USUARIO_LISTAR_POR_SECCION_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_SECCION", Types.NUMERIC),
                        new SqlOutParameter("P_CURSOR", OracleTypes.CURSOR, new IntegranteSeccionRowMapper())
                );
    }
 
    @SuppressWarnings("unchecked")
    public List<IntegrantesSeccionDTO> listarPorSeccion(Long idSeccion) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ID_SECCION", idSeccion);
        Map<String, Object> result = listarPorSeccionCall.execute(params);
        return (List<IntegrantesSeccionDTO>) result.get("P_CURSOR");
    }
}