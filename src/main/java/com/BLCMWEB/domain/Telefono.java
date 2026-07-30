package com.BLCMWEB.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BLCM_TELEFONO_TB")
public class Telefono implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEFONO")
    private Integer idTelefono;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;
}