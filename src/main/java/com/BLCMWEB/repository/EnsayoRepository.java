package com.BLCMWEB.repository;
 
import com.BLCMWEB.domain.EnsayoListadoDTO;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
 
@Repository
public class EnsayoRepository {
 
    private static final String PACKAGE = "BLCM_PROYECTO_PCK";
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    // ===== Calls por SP (insert / update / delete) =====
    private final SimpleJdbcCall insertCall;
    private final SimpleJdbcCall updateCall;
    private final SimpleJdbcCall eliminarCall;
 
    public EnsayoRepository(DataSource dataSource) {
        JdbcTemplate jt = new JdbcTemplate(dataSource);
 
        this.insertCall = new SimpleJdbcCall(jt)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ENSAYO_INSERT_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ENS_FECHA", Types.DATE),
                        new SqlParameter("P_ENS_LUGAR", Types.VARCHAR),
                        new SqlParameter("P_ENS_ID_CALENDARIO", Types.NUMERIC),
                        new SqlParameter("P_ENS_ID_ESTADO", Types.NUMERIC)
                );
 
        this.updateCall = new SimpleJdbcCall(jt)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ENSAYO_UPDATE_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ENS_ID_ENSAYO", Types.NUMERIC),
                        new SqlParameter("P_ENS_FECHA", Types.DATE),
                        new SqlParameter("P_ENS_LUGAR", Types.VARCHAR),
                        new SqlParameter("P_ENS_ID_CALENDARIO", Types.NUMERIC),
                        new SqlParameter("P_ENS_ID_ESTADO", Types.NUMERIC)
                );
 
        this.eliminarCall = new SimpleJdbcCall(jt)
                .withCatalogName(PACKAGE)
                .withProcedureName("BLCM_ENSAYO_DELETE_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ENS_ID_ENSAYO", Types.NUMERIC)
                );
    }
 
    // ===== LISTAR (de tu compañero, con JdbcTemplate) - intacto =====
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
 
    // Buscar uno por ID (para precargar el form de edición). Con JdbcTemplate simple.
    public EnsayoListadoDTO buscarPorId(Integer idEnsayo) {
        String sql = "SELECT ID_ENSAYO, FECHA, LUGAR FROM BLCM_ENSAYO_TB WHERE ID_ENSAYO = ?";
        List<EnsayoListadoDTO> lista = jdbcTemplate.query(sql, ENSAYO_ROW_MAPPER, idEnsayo);
        return lista.isEmpty() ? null : lista.get(0);
    }
 
    // ===== CRUD por SP =====
    public void insertar(java.util.Date fecha, String lugar) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ENS_FECHA", fecha)
                .addValue("P_ENS_LUGAR", lugar)
                .addValue("P_ENS_ID_CALENDARIO", null)  // ignorado por ahora
                .addValue("P_ENS_ID_ESTADO", 1);        // activo
        insertCall.execute(params);
    }
 
    public void actualizar(Integer idEnsayo, java.util.Date fecha, String lugar) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ENS_ID_ENSAYO", idEnsayo)
                .addValue("P_ENS_FECHA", fecha)
                .addValue("P_ENS_LUGAR", lugar)
                .addValue("P_ENS_ID_CALENDARIO", null)
                .addValue("P_ENS_ID_ESTADO", 1);
        updateCall.execute(params);
    }
 
    public void eliminar(Integer idEnsayo) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ENS_ID_ENSAYO", idEnsayo);
        eliminarCall.execute(params);
    }
}