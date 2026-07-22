/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author peper
 */

@Controller
public class PaginasController {
    
    @GetMapping({"/", "/inicio/listado"})
    public String inicio() {
        return "inicio/listado";
    }

    @GetMapping("/calendario/listado")
    public String calendario() {
        return "calendario/listado";
    }

    @GetMapping("/galeria/listado")
    public String galeria() {
        return "galeria/listado";
    }
    
    @GetMapping("/contacto/listado")
    public String contacto() {
        return "contacto/listado";
    }

    @GetMapping("/audiciones/listado")
    public String audiciones() {
        return "audiciones/listado";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/integrantes/listado")
    public String integrantes() {
        return "integrantes/listado";
    }

}
