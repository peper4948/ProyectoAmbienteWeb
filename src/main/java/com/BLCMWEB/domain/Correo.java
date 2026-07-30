package com.BLCMWEB.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BLCM_CORREO_TB")
public class Correo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O secuencia según uses en Oracle
    @Column(name = "ID_CORREO")
    private Integer idCorreo;

    @Column(name = "CORREO")
    private String correo; // O email, dependiendo de cómo lo tengas mapeado

    @Column(name = "ID_ESTADO")
    private Integer idEstado;
}