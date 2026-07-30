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
@Table(name = "FIDE_ESTADOS_TB")
public class Estado implements Serializable {
    private static final long serialVersionUID = 1l; 
    
    
    @Id
    @Column(name="ID_ESTADO")
    private Integer idEstado;
    
    @Column(name="NOMBRE_ESTADO")
    private Integer estado;
    
}
