/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.controller;

import com.BLCMWEB.service.CorreoService;
import com.BLCMWEB.service.DireccionService;
import com.BLCMWEB.service.EstadoService;
import com.BLCMWEB.service.SeccionService;
import com.BLCMWEB.service.TelefonoService;
import com.BLCMWEB.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.BLCMWEB.domain.UsuarioListadoDTO;
import org.springframework.ui.Model;

// IMPORTANTE: Agregamos este import para poder manejar la sesión
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author peper
 */

@Controller
public class PaginasController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TelefonoService telefonoService;
    
    @Autowired
    private SeccionService seccionService;
      
    @Autowired
    private CorreoService correoService;

    @Autowired
    private DireccionService direccionService;
    
    @Autowired
    private EstadoService estadoService;
    
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
    
    @GetMapping("/secciones/listadoDirector")
    public String director(Model model, HttpServletRequest request) { 

        request.getSession(true);


        model.addAttribute("usuarios", usuarioService.readAllUsuario());
        model.addAttribute("nuevoUsuario", new UsuarioListadoDTO());
        
        model.addAttribute("telefonos", telefonoService.readAllTelefono());
        model.addAttribute("correos", correoService.readAllCorreo());
        model.addAttribute("secciones", seccionService.readAllSeccion());
        model.addAttribute("direcciones", direccionService.readAllDireccion());
        model.addAttribute("estados", estadoService.readAllEstado());

        return "secciones/listadoDirector";
    }
    
    @GetMapping("/secciones/listadoLideres")
    public String lideres() {
        return "secciones/listadoLideres";
    }
    
        
    @GetMapping("/secciones/listadoPrincipales")
    public String principales() {
        return "secciones/listadoPrincipales";
    }
    
}