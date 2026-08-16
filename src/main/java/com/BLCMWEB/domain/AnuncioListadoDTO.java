package com.BLCMWEB.domain;

import java.io.Serializable;
import java.sql.Date;

public class AnuncioListadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idAnuncio;
    private Integer autorCedula;
    private String autorNombre;
    private Date fecha;
    private String contenido;

    public Integer getIdAnuncio() { return idAnuncio; }
    public void setIdAnuncio(Integer idAnuncio) { this.idAnuncio = idAnuncio; }

    public Integer getAutorCedula() { return autorCedula; }
    public void setAutorCedula(Integer autorCedula) { this.autorCedula = autorCedula; }

    public String getAutorNombre() { return autorNombre; }
    public void setAutorNombre(String autorNombre) { this.autorNombre = autorNombre; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}