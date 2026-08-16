package com.BLCMWEB.domain;

import java.io.Serializable;

public class AsistenciaMiembroDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer cedula;
    private String nombreCompleto;
    private Integer idEstadoAsistencia; // 3 Presente, 4 Ausente, 5 Justificado, null = sin marcar

    public Integer getCedula() { return cedula; }
    public void setCedula(Integer cedula) { this.cedula = cedula; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Integer getIdEstadoAsistencia() { return idEstadoAsistencia; }
    public void setIdEstadoAsistencia(Integer idEstadoAsistencia) { this.idEstadoAsistencia = idEstadoAsistencia; }
}