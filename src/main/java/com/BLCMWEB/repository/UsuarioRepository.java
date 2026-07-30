package com.BLCMWEB.repository;

import com.BLCMWEB.domain.UsuarioListadoDTO;
import com.BLCMWEB.domain.UsuarioLoginDTO;
import com.BLCMWEB.domain.Usuarios;
import jakarta.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author peper
 */
@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall usuarioInsertCall;
    private SimpleJdbcCall usuarioUpdateCall;
    private SimpleJdbcCall usuarioDeleteCall;
    private SimpleJdbcCall usuarioReadAllCall;
    private SimpleJdbcCall cambiarPasswordCall;
    private SimpleJdbcCall loginCall;
    private SimpleJdbcCall rolesCall;

    // Mapeo utilizando los nombres de las columnas/alias devueltos por el SP
    private static final RowMapper<UsuarioListadoDTO> USUARIO_ROW_MAPPER = new RowMapper<UsuarioListadoDTO>() {
        @Override
        public UsuarioListadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UsuarioListadoDTO dto = new UsuarioListadoDTO();
            
            dto.setCedula(rs.getObject("CEDULA") != null ? rs.getInt("CEDULA") : null);
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
            dto.setNombreSeccion(rs.getString("NOMBRE_SECCION")); 
            dto.setCorreo(rs.getString("EMAIL")); // Capturamos el alias del SP
            dto.setTelefono(rs.getString("TELEFONO"));
            
            // Aquí capturamos la palabra ("Activo" / "Inactivo")
            dto.setNombreEstado(rs.getString("NOMBRE_ESTADO")); 
            
            dto.setIdEstado(rs.getObject("ID_ESTADO") != null ? rs.getInt("ID_ESTADO") : null);
            
            // La fecha fue removida del SP, así que la dejamos en null para el DTO
            dto.setFechaIngreso(null); 
            
            return dto;
        }
    };

    @PostConstruct
    public void init() {
        usuarioInsertCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_USUARIO_INSERT_SP");

        usuarioUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_USUARIO_UPDATE_SP");

        usuarioDeleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_USUARIO_DELETE_SP");

        usuarioReadAllCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_USUARIO_LISTAR_SP")
                .returningResultSet("P_CURSOR", USUARIO_ROW_MAPPER);
        
        loginCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_LOGIN_USUARIO_SP")
                .returningResultSet("P_CURSOR", LOGIN_ROW_MAPPER);

        rolesCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_ROLES_USUARIO_SP")
                .returningResultSet("P_CURSOR", ROL_ROW_MAPPER);
        
        cambiarPasswordCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BLCM_PROYECTO_PCK")
                .withProcedureName("BLCM_USUARIO_CAMBIAR_CLAVE_SP");
    }

    public void insertarUsuario(Usuarios usuario) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_USUARIO_CEDULA", usuario.getCedula());
        params.put("P_USUARIO_NOMBRE", usuario.getNombre());
        params.put("P_USUARIO_APELLIDO_PAT", usuario.getPrimerApellido());
        params.put("P_USUARIO_APELLIDO_MAT", usuario.getSegundoApellido());
        params.put("P_USUARIO_CLAVE", usuario.getClave());
        params.put("P_USUARIO_ID_SECCION", usuario.getIdSeccion());
        params.put("P_USUARIO_ID_ESTADO", usuario.getIdEstado());
        
        // Nuevas llaves foráneas
        params.put("P_USUARIO_ID_CORREO", usuario.getIdCorreo());
        params.put("P_USUARIO_ID_TELEFONO", usuario.getIdTelefono());

        usuarioInsertCall.execute(params);
    }

    public void actualizarUsuario(Usuarios usuario) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_USUARIO_CEDULA", usuario.getCedula());
        params.put("P_USUARIO_NOMBRE", usuario.getNombre());
        params.put("P_USUARIO_APELLIDO_PAT", usuario.getPrimerApellido());
        params.put("P_USUARIO_APELLIDO_MAT", usuario.getSegundoApellido());
        params.put("P_USUARIO_ID_SECCION", usuario.getIdSeccion());
        params.put("P_USUARIO_ID_ESTADO", usuario.getIdEstado());
        
        // Nuevas llaves foráneas
        params.put("P_USUARIO_ID_CORREO", usuario.getIdCorreo());
        params.put("P_USUARIO_ID_TELEFONO", usuario.getIdTelefono());

        usuarioUpdateCall.execute(params);
    }

    @SuppressWarnings("unchecked")
    public List<UsuarioListadoDTO> readAllUsuario() {
        Map<String, Object> result = usuarioReadAllCall.execute();
        return (List<UsuarioListadoDTO>) result.get("P_CURSOR");
    }

    public void deleteUsuario(Usuarios usuario) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_USUARIO_CEDULA", usuario.getCedula());
        usuarioDeleteCall.execute(params);
    }

    @SuppressWarnings("unchecked")
    public UsuarioLoginDTO buscarPorCorreo(String correo) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_CORREO", correo);
        Map<String, Object> result = loginCall.execute(params);
        List<UsuarioLoginDTO> lista = (List<UsuarioLoginDTO>) result.get("P_CURSOR");
        return (lista == null || lista.isEmpty()) ? null : lista.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<String> buscarRolesPorCedula(Integer cedula) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_CEDULA", cedula);
        Map<String, Object> result = rolesCall.execute(params);
        List<String> roles = (List<String>) result.get("P_CURSOR");
        return roles == null ? new ArrayList<>() : roles;
    }

    public void cambiarClave(Integer cedula, String passwordHash) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_CEDULA", cedula);
        params.put("P_CLAVE", passwordHash);
        cambiarPasswordCall.execute(params);
    }

    // Orden del SELECT en BLCM_LOGIN_USUARIO_SP:
    // 1 CEDULA, 2 CLAVE, 3 NOMBRE, 4 APELLIDO_PATERNO, 5 ID_ESTADO, 6 EMAIL
    private static final RowMapper<UsuarioLoginDTO> LOGIN_ROW_MAPPER = (rs, rowNum) -> {
        UsuarioLoginDTO dto = new UsuarioLoginDTO();
        dto.setCedula(rs.getObject(1) != null ? rs.getInt(1) : null);
        dto.setClave(rs.getString(2));
        dto.setNombre(rs.getString(3));
        dto.setApellidoPaterno(rs.getString(4));
        dto.setIdEstado(rs.getObject(5) != null ? rs.getInt(5) : null);
        return dto;
    };

    // Mapeo para los roles devueltos por BLCM_ROLES_USUARIO_SP (columna 2: NOMBRE_ROL)
    private static final RowMapper<String> ROL_ROW_MAPPER = (rs, rowNum) -> rs.getString(2);
}