/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author peper
 */

@Entity
@Table(name = "BLCM_USUARIOS_TB")
public class Seccion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_SECCION")
    private Integer idSeccion;    
    
    @Column(name="NOMBRE_SECCION", length=100)
    private String nombre;
    
    @Column(name="DESCRIPCION", length=300)
    private String descripcion;
   
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    
    
}

   
