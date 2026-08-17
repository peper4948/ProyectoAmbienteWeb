package com.BLCMWEB.controller;

import com.BLCMWEB.domain.CalendarioEvento;
import com.BLCMWEB.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secciones")
public class CalendarioController {

    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/listadoCalendario")
    public String listarCalendario(Model model) {
        model.addAttribute("eventos", calendarioService.listarTodos());
        model.addAttribute("total", calendarioService.contarTotal());
        model.addAttribute("totalActivos", calendarioService.contarActivos());
        return "secciones/listadoCalendario";
    }

    @PostMapping("/calendario/guardar")
    public String guardarEvento(@ModelAttribute CalendarioEvento evento) {
        if (evento.getIdEstado() == null) {
            evento.setIdEstado(1L);
        }
        calendarioService.guardarEvento(evento);
        
        return "redirect:/secciones/listadoDirector?tab=calendario";
    }

    @PostMapping("/listadoCalendario/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        CalendarioEvento e = calendarioService.obtenerPorId(id);
        if (e != null) {
            e.setIdEstado(0L);   // baja lógica
            calendarioService.guardarEvento(e);
        }
        return "redirect:/secciones/listadoCalendario";
    }

    @GetMapping("/fragmentosCalendario")
    public String calendarioPublico(Model model) {
        model.addAttribute("eventos", calendarioService.listarTodos());
        return "calendario/listado";
    }
}