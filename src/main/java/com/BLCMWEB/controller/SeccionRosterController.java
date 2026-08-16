/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.controller;

import com.BLCMWEB.domain.IntegrantesSeccionDTO;
import com.BLCMWEB.service.SeccionRosterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
import java.util.List;
 

@Controller
public class SeccionRosterController {
 
    private final SeccionRosterService service;
 
    public SeccionRosterController(SeccionRosterService service) {
        this.service = service;
    }
 
    @GetMapping("/secciones/listado/{idSeccion}")
    public String listadoSeccion(@PathVariable("idSeccion") Long idSeccion, Model model) {
        List<IntegrantesSeccionDTO> integrantes = service.listarPorSeccion(idSeccion);
 
        model.addAttribute("integrantes", integrantes);
        model.addAttribute("total", service.total(integrantes));
        model.addAttribute("totalActivos", service.totalActivos(integrantes));
        model.addAttribute("idSeccion", idSeccion);
 
        return "secciones/listadoSeccion";
    }
}