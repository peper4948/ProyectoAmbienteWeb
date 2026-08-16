/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.domain;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;


@Data
public class UsuarioListadoDTO implements Serializable {
    private static final long serialVersionUID = 1l;
    
    private Integer cedula;          // Es VARCHAR2 en la BD
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreSeccion;   // Viene de BLCM_SECCION_TB
    private String correo;          // Viene de BLCM_CORREO_TB
    private String telefono;        // Viene de BLCM_TELEFONO_TB
    private Integer idEstado;
    private String fechaIngreso;    // O Date según lo manejes
    private Integer idSeccion;
    private String clave;
    private String nombreEstado;

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
    
    
    
    

}
