package com.BLCMWEB.repository;

import com.BLCMWEB.domain.AnuncioListadoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnuncioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            WHERE u.ID_SECCION = ? AND an.ID_ESTADO = 1
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

    public void insertarAnuncio(Integer autorCedula, String contenido) {
        jdbcTemplate.update(
            "INSERT INTO BLCM_ANUNCIO_TB (AUTOR_CEDULA, FECHA, CONTENIDO, ID_ESTADO) VALUES (?, SYSDATE, ?, 1)",
            autorCedula, contenido
        );
    }

    public void eliminarAnuncio(Integer idAnuncio) {
        jdbcTemplate.update("UPDATE BLCM_ANUNCIO_TB SET ID_ESTADO = 2 WHERE ID_ANUNCIO = ?", idAnuncio);
    }
}