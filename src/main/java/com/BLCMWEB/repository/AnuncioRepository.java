package com.BLCMWEB.repository;

import com.BLCMWEB.domain.AnuncioDTO;
import com.BLCMWEB.domain.AnuncioListadoDTO;
import com.BLCMWEB.controller.AnuncioRowMapper;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final JdbcTemplate jdbcTemplate;

    public AnuncioRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.listarCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ANUNCIO_LISTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("P_CURSOR", OracleTypes.CURSOR, new AnuncioRowMapper())
                );

        this.insertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ANUNCIO_INSERT_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ANU_AUTOR_CEDULA", Types.NUMERIC),
                        new SqlParameter("P_ANU_FECHA", Types.DATE),
                        new SqlParameter("P_ANU_CONTENIDO", Types.VARCHAR),
                        new SqlParameter("P_ANU_ID_ESTADO", Types.NUMERIC)
                );

        this.eliminarCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ANUNCIO_DELETE_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ANU_ID_ANUNCIO", Types.NUMERIC)
                );
    }

    // ===== Dashboard (Pepe) =====
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

    // ===== Panel de líderes  =====
    public List<AnuncioListadoDTO> listarPorSeccion(Integer idSeccion) {
        String sql = """
        SELECT
            an.ID_ANUNCIO AS ID_ANUNCIO,
            an.AUTOR_CEDULA AS AUTOR_CEDULA,
            u.NOMBRE || ' ' || u.PRIMER_APELLIDO AS AUTOR_NOMBRE,
            an.FECHA AS FECHA,
            an.CONTENIDO AS CONTENIDO
        FROM BLCM_ANUNCIO_TB an
        JOIN BLCM_USUARIOS_TB u ON u.CEDULA = an.AUTOR_CEDULA
        WHERE an.ID_SECCION = ? AND an.ID_ESTADO = 1
        ORDER BY an.FECHA DESC
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AnuncioListadoDTO dto = new AnuncioListadoDTO();
            dto.setIdAnuncio(rs.getInt("ID_ANUNCIO"));
            dto.setAutorCedula(rs.getInt("AUTOR_CEDULA"));
            dto.setAutorNombre(rs.getString("AUTOR_NOMBRE"));
            dto.setFecha(rs.getDate("FECHA"));
            dto.setContenido(rs.getString("CONTENIDO"));
            return dto;
        }, idSeccion);
    }

    public void insertarAnuncio(Integer autorCedula, Integer idSeccion, String contenido) {
        jdbcTemplate.update(
                "INSERT INTO BLCM_ANUNCIO_TB (AUTOR_CEDULA, ID_SECCION, FECHA, CONTENIDO, ID_ESTADO) VALUES (?, ?, SYSDATE, ?, 1)",
                autorCedula, idSeccion, contenido
        );
    }

    public void eliminarAnuncio(Integer idAnuncio) {
        jdbcTemplate.update("UPDATE BLCM_ANUNCIO_TB SET ID_ESTADO = 2 WHERE ID_ANUNCIO = ?", idAnuncio);
    }
}
