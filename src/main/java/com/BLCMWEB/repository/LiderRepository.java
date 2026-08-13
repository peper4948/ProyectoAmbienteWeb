package com.BLCMWEB.repository;

import com.BLCMWEB.domain.LiderListadoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LiderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final int ID_ROL_LIDER = 14;

    private static final RowMapper<LiderListadoDTO> LIDER_ROW_MAPPER = (rs, rowNum) -> {
        LiderListadoDTO dto = new LiderListadoDTO();
        dto.setCedula(rs.getInt("CEDULA"));
        dto.setNombre(rs.getString("NOMBRE"));
        dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
        dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
        dto.setCorreo(rs.getString("CORREO"));
        dto.setTelefono(rs.getString("TELEFONO"));
        dto.setIdSeccion(rs.getObject("ID_SECCION") != null ? rs.getInt("ID_SECCION") : null);
        dto.setNombreSeccion(rs.getString("NOMBRE_SECCION"));
        dto.setIdEstado(rs.getInt("ID_ESTADO"));
        dto.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
        return dto;
    };

    public List<LiderListadoDTO> listarLideres() {
        String sql = """
            SELECT
                u.CEDULA          AS CEDULA,
                u.NOMBRE          AS NOMBRE,
                u.PRIMER_APELLIDO AS APELLIDO_PATERNO,
                u.SEGUNDO_APELLIDO AS APELLIDO_MATERNO,
                c.CORREO          AS CORREO,
                t.TELEFONO        AS TELEFONO,
                u.ID_SECCION      AS ID_SECCION,
                s.NOMBRE_SECCION  AS NOMBRE_SECCION,
                ur.ID_ESTADO      AS ID_ESTADO,
                e.NOMBRE_ESTADO   AS NOMBRE_ESTADO
            FROM BLCM_USUARIO_ROL_TB ur
            JOIN BLCM_USUARIOS_TB u  ON u.CEDULA = ur.CEDULA
            LEFT JOIN BLCM_CORREO_TB c    ON c.ID_CORREO = u.ID_CORREO
            LEFT JOIN BLCM_TELEFONO_TB t  ON t.ID_TELEFONO = u.ID_TELEFONO
            LEFT JOIN BLCM_SECCION_TB s   ON s.ID_SECCION = u.ID_SECCION
            JOIN BLCM_ESTADOS_TB e   ON e.ID_ESTADO = ur.ID_ESTADO
            WHERE ur.ID_ROL = ?
            ORDER BY ur.ID_ESTADO ASC, u.NOMBRE ASC
            """;
        return jdbcTemplate.query(sql, LIDER_ROW_MAPPER, ID_ROL_LIDER);
    }

    private Integer buscarIdUsuarioRol(Integer cedula) {
        List<Integer> ids = jdbcTemplate.query(
                "SELECT ID_USUARIO_ROL FROM BLCM_USUARIO_ROL_TB WHERE CEDULA = ? AND ID_ROL = ?",
                (rs, rowNum) -> rs.getInt("ID_USUARIO_ROL"),
                cedula, ID_ROL_LIDER
        );
        return ids.isEmpty() ? null : ids.get(0);
    }

    public void asignarLider(Integer cedula) {
        Integer idUsuarioRol = buscarIdUsuarioRol(cedula);
        if (idUsuarioRol == null) {
            // Nunca ha sido líder: se crea el registro (ID_USUARIO_ROL lo llena el trigger)
            jdbcTemplate.update(
                    "INSERT INTO BLCM_USUARIO_ROL_TB (CEDULA, ID_ROL, ID_ESTADO) VALUES (?, ?, 1)",
                    cedula, ID_ROL_LIDER
            );
        } else {
            // Ya tuvo el rol antes (estaba inactivo): se reactiva
            jdbcTemplate.update(
                    "UPDATE BLCM_USUARIO_ROL_TB SET ID_ESTADO = 1 WHERE ID_USUARIO_ROL = ?",
                    idUsuarioRol
            );
        }
    }

    public void quitarLider(Integer cedula) {
        jdbcTemplate.update(
                "UPDATE BLCM_USUARIO_ROL_TB SET ID_ESTADO = 2 WHERE CEDULA = ? AND ID_ROL = ?",
                cedula, ID_ROL_LIDER
        );
    }
}