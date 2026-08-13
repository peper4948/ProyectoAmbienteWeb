package com.BLCMWEB.domain;

import java.io.Serializable;
import java.sql.Date;

public class EnsayoListadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idEnsayo;
    private Date fecha;
    private String lugar;

    public Integer getIdEnsayo() { return idEnsayo; }
    public void setIdEnsayo(Integer idEnsayo) { this.idEnsayo = idEnsayo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
}