package com.BLCMWEB.repository;

import com.BLCMWEB.domain.EnsayoListadoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EnsayoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<EnsayoListadoDTO> ENSAYO_ROW_MAPPER = (rs, rowNum) -> {
        EnsayoListadoDTO dto = new EnsayoListadoDTO();
        dto.setIdEnsayo(rs.getInt("ID_ENSAYO"));
        dto.setFecha(rs.getDate("FECHA"));
        dto.setLugar(rs.getString("LUGAR"));
        return dto;
    };

    public List<EnsayoListadoDTO> listarEnsayos() {
        String sql = "SELECT ID_ENSAYO, FECHA, LUGAR FROM BLCM_ENSAYO_TB WHERE ID_ESTADO = 1 ORDER BY FECHA DESC";
        return jdbcTemplate.query(sql, ENSAYO_ROW_MAPPER);
    }
}