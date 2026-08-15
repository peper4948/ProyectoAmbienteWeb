
package com.BLCMWEB.repository;

import com.BLCMWEB.domain.GaleriaListadoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GaleriaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_BASE =
            "SELECT ID_MEDIA, TITULO, URL, TIPO, FECHA, ID_ESTADO "
            + "FROM BLCM_GALERIA_TB ";

    private static final RowMapper<GaleriaListadoDTO> MAPPER = (rs, rowNum) -> {
        GaleriaListadoDTO dto = new GaleriaListadoDTO();
        dto.setIdMedia(rs.getInt("ID_MEDIA"));
        dto.setTitulo(rs.getString("TITULO"));
        dto.setUrl(rs.getString("URL"));
        dto.setTipo(rs.getString("TIPO"));
        dto.setFecha(rs.getDate("FECHA"));
        dto.setIdEstado(rs.getInt("ID_ESTADO"));
        return dto;
    };

    public List<GaleriaListadoDTO> listarTodas() {
        return jdbcTemplate.query(SELECT_BASE + "ORDER BY FECHA DESC, ID_MEDIA DESC", MAPPER);
    }

    public List<GaleriaListadoDTO> listarActivas() {
        return jdbcTemplate.query(
                SELECT_BASE + "WHERE ID_ESTADO = 1 ORDER BY FECHA DESC, ID_MEDIA DESC", MAPPER);
    }

    public GaleriaListadoDTO buscarPorId(Integer idMedia) {
        List<GaleriaListadoDTO> resultado = jdbcTemplate.query(
                SELECT_BASE + "WHERE ID_MEDIA = ?", MAPPER, idMedia);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void insertarFoto(GaleriaListadoDTO dto) {
        jdbcTemplate.update(
                "INSERT INTO BLCM_GALERIA_TB (TITULO, URL, TIPO, FECHA, ID_ESTADO) "
                + "VALUES (?, ?, ?, SYSDATE, ?)",
                dto.getTitulo(),
                dto.getUrl(),
                dto.getTipo(),
                dto.getIdEstado() != null ? dto.getIdEstado() : 1);
    }

    public void actualizarFoto(GaleriaListadoDTO dto) {
        jdbcTemplate.update(
                "UPDATE BLCM_GALERIA_TB SET TITULO = ?, URL = ?, TIPO = ?, ID_ESTADO = ? "
                + "WHERE ID_MEDIA = ?",
                dto.getTitulo(),
                dto.getUrl(),
                dto.getTipo(),
                dto.getIdEstado() != null ? dto.getIdEstado() : 1,
                dto.getIdMedia());
    }

    public void cambiarEstado(Integer idMedia, Integer idEstado) {
        jdbcTemplate.update("UPDATE BLCM_GALERIA_TB SET ID_ESTADO = ? WHERE ID_MEDIA = ?", idEstado, idMedia);
    }

    public void eliminarFisico(Integer idMedia) {
        jdbcTemplate.update("DELETE FROM BLCM_GALERIA_TB WHERE ID_MEDIA = ?", idMedia);
    }
}
