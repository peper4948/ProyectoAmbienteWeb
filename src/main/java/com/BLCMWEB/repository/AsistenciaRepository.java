package com.BLCMWEB.repository;

import com.BLCMWEB.domain.AsistenciaMiembroDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AsistenciaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AsistenciaMiembroDTO> listarAsistenciaPorEnsayo(Integer idSeccion, Integer idEnsayo) {
        String sql = """
            SELECT
                u.CEDULA AS CEDULA,
                u.NOMBRE || ' ' || u.PRIMER_APELLIDO AS NOMBRE_COMPLETO,
                a.ID_ESTADO AS ID_ESTADO_ASISTENCIA
            FROM BLCM_USUARIOS_TB u
            LEFT JOIN BLCM_ASISTENCIA_TB a
                ON a.CEDULA = u.CEDULA AND a.ID_ENSAYO = ?
            WHERE u.ID_SECCION = ? AND u.ID_ESTADO = 1
            ORDER BY u.NOMBRE
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AsistenciaMiembroDTO dto = new AsistenciaMiembroDTO();
            dto.setCedula(rs.getInt("CEDULA"));
            dto.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
            int estado = rs.getInt("ID_ESTADO_ASISTENCIA");
            dto.setIdEstadoAsistencia(rs.wasNull() ? null : estado);
            return dto;
        }, idEnsayo, idSeccion);
    }

    public void guardarAsistencia(Integer idEnsayo, Integer cedula, Integer idEstadoAsistencia) {
        String sql = """
            MERGE INTO BLCM_ASISTENCIA_TB dest
            USING (SELECT ? AS ID_ENSAYO, ? AS CEDULA FROM DUAL) src
            ON (dest.ID_ENSAYO = src.ID_ENSAYO AND dest.CEDULA = src.CEDULA)
            WHEN MATCHED THEN
                UPDATE SET dest.ID_ESTADO = ?
            WHEN NOT MATCHED THEN
                INSERT (ID_ENSAYO, CEDULA, ID_ESTADO)
                VALUES (src.ID_ENSAYO, src.CEDULA, ?)
            """;
        jdbcTemplate.update(sql, idEnsayo, cedula, idEstadoAsistencia, idEstadoAsistencia);
    }
}