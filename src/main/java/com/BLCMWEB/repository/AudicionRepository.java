package com.BLCMWEB.repository;

import com.BLCMWEB.domain.AudicionListadoDTO;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class AudicionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall audicionInsertCall;
    private SimpleJdbcCall audicionListarCall;
    private SimpleJdbcCall audicionEstadoUpdateCall;

    @PostConstruct
    public void init() {
        audicionInsertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_AUDICION_INSERT_SP");

        audicionListarCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_AUDICION_LISTAR_SP")
                .returningResultSet("P_CURSOR",
                        BeanPropertyRowMapper.newInstance(AudicionListadoDTO.class));

        audicionEstadoUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_AUDICION_ESTADO_UPDATE_SP");
    }

    public void insertarAudicion(Long cedula, String nombre, String apellidoPaterno, String apellidoMaterno,
            String email, String telefono, Integer idSeccion, String comentarios) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_AUD_CEDULA", cedula);
        params.put("P_AUD_NOMBRE", nombre);
        params.put("P_AUD_APELLIDO_PAT", apellidoPaterno);
        params.put("P_AUD_APELLIDO_MAT", apellidoMaterno);
        params.put("P_AUD_EMAIL", email);
        params.put("P_AUD_TELEFONO", telefono);
        params.put("P_AUD_ID_SECCION", idSeccion);
        params.put("P_AUD_ID_ESTADO", 1);        // <-- NUEVO: 1 = Activo, según tu tabla BLCM_ESTADOS_TB
        params.put("P_AUD_COMENTARIOS", comentarios);
        audicionInsertCall.execute(params);
    }

    @SuppressWarnings("unchecked")
    public List<AudicionListadoDTO> readAllAudiciones() {
        Map<String, Object> result = audicionListarCall.execute();
        return (List<AudicionListadoDTO>) result.get("P_CURSOR");
    }

    public void actualizarEstado(Integer idAudicion, Integer idEstado) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_AUD_ID_AUDICION", idAudicion);
        params.put("P_AUD_ID_ESTADO", idEstado);
        audicionEstadoUpdateCall.execute(params);
    }
}
