/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.controller;

 
import com.BLCMWEB.domain.IntegrantesSeccionDTO;
import org.springframework.jdbc.core.RowMapper;
 
import java.sql.ResultSet;
import java.sql.SQLException;
 

public class IntegranteSeccionRowMapper implements RowMapper<IntegrantesSeccionDTO> {
 
    @Override
    public IntegrantesSeccionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        IntegrantesSeccionDTO dto = new IntegrantesSeccionDTO();
        dto.setCedula(rs.getLong(1));            // CEDULA
        dto.setNombre(rs.getString(2));          // NOMBRE
        dto.setPrimerApellido(rs.getString(3));  // PRIMER_APELLIDO
        dto.setSegundoApellido(rs.getString(4)); // SEGUNDO_APELLIDO
        dto.setNombreEstado(rs.getString(5));    // NOMBRE_ESTADO
        int idEstado = rs.getInt(6);             // ID_ESTADO
        dto.setIdEstado(rs.wasNull() ? null : idEstado);
        dto.setNombreRol(rs.getString(7));       // NOMBRE_ROL
        return dto;
    }
}