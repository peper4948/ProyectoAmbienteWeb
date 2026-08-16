/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class UsuarioLoginDTO implements Serializable {
    
    private static final long serialVersionUID = 1l;
    
    private Integer cedula;
    private String clave;
    private String nombre;
    private String apellidoPaterno;
    private Integer idEstado;
    private String email; 

    
    
    
}
