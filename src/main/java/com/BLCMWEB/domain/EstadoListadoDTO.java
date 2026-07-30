/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.domain;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author peper
 */
@Data
public class EstadoListadoDTO implements Serializable {
    private static final long serialVersionUID = 1l;
    
    private Integer idEstado;
    
    private String estado;

}
