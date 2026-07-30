package com.BLCMWEB.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BLCM_USUARIOS_TB")
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CEDULA")
    private Integer cedula;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;

    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;

    @Column(name = "CLAVE")
    private String clave;

    @Column(name = "ID_SECCION")
    private Integer idSeccion;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;

    @Column(name = "ID_CORREO")
    private Integer idCorreo;

    @Column(name = "ID_TELEFONO")
    private Integer idTelefono;
}