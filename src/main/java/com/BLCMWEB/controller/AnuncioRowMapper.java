/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.controller;

/**
 *
 * @author peper
 */
import com.BLCMWEB.domain.AnuncioDTO;
import org.springframework.jdbc.core.RowMapper;
 
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * RowMapper POSICIONAL. Orden del SELECT del SP:
 *   ID_ANUNCIO, AUTOR_CEDULA, NOMBRE, PRIMER_APELLIDO,
 *   NOMBRE_ROL, FECHA, CONTENIDO, ID_ESTADO
 */
public class AnuncioRowMapper implements RowMapper<AnuncioDTO> {
 
@Override
public AnuncioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    AnuncioDTO dto = new AnuncioDTO();
    dto.setIdAnuncio(rs.getLong(1));         // ID_ANUNCIO
    int ced = rs.getInt(2);                  // AUTOR_CEDULA  <-- getInt, no getInteger
    dto.setAutorCedula(rs.wasNull() ? null : ced);
    dto.setNombre(rs.getString(3));          // NOMBRE
    dto.setPrimerApellido(rs.getString(4));  // PRIMER_APELLIDO
    dto.setNombreRol(rs.getString(5));       // NOMBRE_ROL
    dto.setFecha(rs.getDate(6));             // FECHA
    dto.setContenido(rs.getString(7));       // CONTENIDO
    int est = rs.getInt(8);                  // ID_ESTADO
    dto.setIdEstado(rs.wasNull() ? null : est);
    return dto;

    }
}
