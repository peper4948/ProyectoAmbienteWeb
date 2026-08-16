/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.domain;

/**
 *
 * @author peper
 */
public class IntegrantesSeccionDTO {
 
    private Long cedula;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreEstado;
    private Integer idEstado;
    private String nombreRol;
  
    // Nombre completo, útil para pintarlo directo en la tabla
    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (nombre != null) sb.append(nombre);
        if (primerApellido != null) sb.append(" ").append(primerApellido);
        if (segundoApellido != null) sb.append(" ").append(segundoApellido);
        return sb.toString().trim();
    }
 
    // ¿Es líder de la sección?
    public boolean isLider() {
        return nombreRol != null && nombreRol.trim().equalsIgnoreCase("LIDER DE SECCION");
    }
 
    // ¿Está activo? (ID_ESTADO = 1)
    public boolean isActivo() {
        return idEstado != null && idEstado == 1;
    }
 
    public Long getCedula() { return cedula; }
    public void setCedula(Long cedula) { this.cedula = cedula; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
 
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
 
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
 
    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }
 
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}