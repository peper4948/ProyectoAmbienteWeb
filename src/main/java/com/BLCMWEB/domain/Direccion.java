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
import lombok.Data;

/**
 *
 * @author peper
 */

@Data
@Entity
@Table(name = "BLCM_DIRECCION_TB")
public class Direccion implements Serializable {
    private static final long serialVersionUID = 1l;
    
    @Id
    @Column(name="ID_DIRECCION")
    private Integer idDireccion;
    
    @Column(name="ID_PROVINCIA")
    private Integer idProvincia;
    
    @Column(name="ID_CANTON")
    private Integer idCanton;
    
    @Column(name="ID_DISTRITO")
    private Integer idDistrito;
    
    @Column(name="DETALLES_EXTRA", length=300)
    private String otrosDetalles;
    
    @Column(name="ID_ESTADO")
    private Integer idEstado; 
}

