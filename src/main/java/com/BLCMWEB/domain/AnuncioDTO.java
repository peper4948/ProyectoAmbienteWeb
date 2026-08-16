/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.domain;

/**
 *
 * @author peper
 */
import java.util.Date;
import lombok.Data;
 
/**
 * Anuncio para mostrar en el dashboard, con datos del autor ya resueltos.
 */
@Data
public class AnuncioDTO {
 
    private Long idAnuncio;
    private Integer autorCedula;
    private String nombre;
    private String primerApellido;
    private String nombreRol;
    private Date fecha;
    private String contenido;
    private Integer idEstado;
 
    public AnuncioDTO() {
    }
 
    // Etiqueta del autor: usa el rol si existe (Director / Líder de Sección),
    // y si no, el nombre de la persona.
    public String getAutorEtiqueta() {
        if (nombreRol != null && !nombreRol.isBlank()) {
            return nombreRol.trim();
        }
        StringBuilder sb = new StringBuilder();
        if (nombre != null) sb.append(nombre);
        if (primerApellido != null) sb.append(" ").append(primerApellido);
        String s = sb.toString().trim();
        return s.isEmpty() ? "Anónimo" : s;
    }
 
}