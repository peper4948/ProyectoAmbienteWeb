
package com.BLCMWEB.domain;

import java.io.Serializable;
import java.sql.Date;

public class GaleriaListadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idMedia;
    private String titulo;
    private String url;
    private String tipo;
    private Date fecha;
    private Integer idEstado;

    public Integer getIdMedia() { return idMedia; }
    public void setIdMedia(Integer idMedia) { this.idMedia = idMedia; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }
}
