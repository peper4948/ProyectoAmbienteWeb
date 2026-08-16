/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.repository;

/**
 *
 * @author peper
 */
import com.BLCMWEB.domain.AnuncioDTO;
import com.BLCMWEB.controller.AnuncioRowMapper;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
 
import javax.sql.DataSource;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
 
@Repository
public class AnuncioRepository {
 
    private static final String PACKAGE = "BLCM_PROYECTO_PCK";
 
    private final SimpleJdbcCall listarCall;
    private final SimpleJdbcCall insertCall;
    private final SimpleJdbcCall eliminarCall;
 
    public AnuncioRepository(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 
        this.listarCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ANUNCIO_LISTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("P_CURSOR", OracleTypes.CURSOR, new AnuncioRowMapper())
                );
 
        // Ajustá los nombres de parámetros a los de TU BLCM_ANUNCIO_INSERT_SP.
        // Se asume: (P_ANU_AUTOR_CEDULA, P_ANU_FECHA, P_ANU_CONTENIDO)
        this.insertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ANUNCIO_INSERT_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ANU_AUTOR_CEDULA", Types.NUMERIC),
                        new SqlParameter("P_ANU_FECHA", Types.DATE),
                        new SqlParameter("P_ANU_CONTENIDO", Types.VARCHAR),
                        new SqlParameter("P_ANU_ID_ESTADO", Types.NUMERIC) // <-- faltaba
                );
        this.eliminarCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ANUNCIO_DELETE_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ANU_ID_ANUNCIO", Types.NUMERIC)
                );
    }
 
    @SuppressWarnings("unchecked")
    public List<AnuncioDTO> listar() {
        Map<String, Object> result = listarCall.execute();
        return (List<AnuncioDTO>) result.get("P_CURSOR");
    }

    public void insertar(Integer autorCedula, String contenido) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ANU_AUTOR_CEDULA", autorCedula)
                .addValue("P_ANU_FECHA", new Date())
                .addValue("P_ANU_CONTENIDO", contenido)
                .addValue("P_ANU_ID_ESTADO", 1);
        insertCall.execute(params);
    }
    public void eliminar(Integer idAnuncio) {
    MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("P_ANU_ID_ANUNCIO", idAnuncio);
    eliminarCall.execute(params);
}
}