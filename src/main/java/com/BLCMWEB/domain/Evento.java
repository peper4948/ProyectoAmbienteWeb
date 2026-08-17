package com.BLCMWEB.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
@Data
@Entity
@Table(name = "EVENTO")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDateTime fechaEvento;

    @Column(name = "tipo_evento", length = 50)
    private String tipoEvento;

    @Column(name = "estado", length = 30)
    private String estado;

    // Constructor vacío
    public Evento() {
    }

    // Constructor con parámetros
    public Evento(String titulo, String descripcion, LocalDateTime fechaEvento, String tipoEvento, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.tipoEvento = tipoEvento;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getIdEvento() {
        return idEvento;
    }
}