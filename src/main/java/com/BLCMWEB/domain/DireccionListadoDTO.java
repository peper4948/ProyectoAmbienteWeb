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
public class DireccionListadoDTO implements Serializable {
    private static final long serialVersionUID = 1l;
    
    private Integer idDireccion;
    
    private Integer idProvincia;
    
    private Integer idCanton;
    
    private Integer idDistrito;
    
    private String detallesExtra;
    private String provincia;
    private String canton;
    private String distrito;
    private String estado;
    
    private Integer idEstado; 

}
