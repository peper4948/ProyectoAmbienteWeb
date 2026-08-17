package com.BLCMWEB.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "CALENDARIO_EVENTO")
public class CalendarioEvento {

    @Id
    @Column(name = "id_calendario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalendario;

    private String descripcion;
    private String lugar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(name = "id_estado")
    private Long idEstado;

        @Column(name = "tipo_evento")
    private String tipoEvento;

    public Long getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Long idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}