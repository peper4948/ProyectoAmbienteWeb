package com.BLCMWEB.controller;

import com.BLCMWEB.domain.Seccion;
import com.BLCMWEB.domain.SeccionListadoDTO;
import com.BLCMWEB.service.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/secciones")
public class SeccionController {

    @Autowired
    private SeccionService seccionService;

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("seccion", new SeccionListadoDTO());
        return "secciones/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("seccion", seccionService.buscarPorId(id));
        return "secciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute SeccionListadoDTO dto, RedirectAttributes ra) {
        try {
            Seccion seccion = new Seccion();
            seccion.setIdSeccion(dto.getIdSeccion());
            seccion.setNombre(dto.getNombreSeccion());
            seccion.setDescripcion(dto.getDescripcion());
            seccion.setIdEstado(dto.getIdEstado() != null ? dto.getIdEstado() : 1);

            if (dto.getIdSeccion() != null && seccionService.buscarPorId(dto.getIdSeccion()) != null) {
                seccionService.actualizarSeccion(seccion);
            } else {
                seccionService.insertarSeccion(seccion);
            }

            ra.addFlashAttribute("todoOk", "Sección guardada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al guardar la sección: " + ex.getMessage());
        }
        return "redirect:/audiciones/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idSeccion") Integer idSeccion, RedirectAttributes ra) {
        try {
            seccionService.eliminarSeccion(idSeccion); // pone ID_ESTADO = 2 (Inactivo)
            ra.addFlashAttribute("todoOk", "Sección desactivada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al desactivar la sección");
        }
        return "redirect:/audiciones/listado";
    }

    @PostMapping("/activar")
    public String activar(@RequestParam("idSeccion") Integer idSeccion, RedirectAttributes ra) {
        try {
            SeccionListadoDTO actual = seccionService.buscarPorId(idSeccion);
            if (actual == null) {
                ra.addFlashAttribute("error", "Sección no encontrada");
                return "redirect:/audiciones/listado";
            }
            Seccion seccion = new Seccion();
            seccion.setIdSeccion(actual.getIdSeccion());
            seccion.setNombre(actual.getNombreSeccion());
            seccion.setDescripcion(actual.getDescripcion());
            seccion.setIdEstado(1); // Activo

            seccionService.actualizarSeccion(seccion);
            ra.addFlashAttribute("todoOk", "Sección activada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al activar la sección");
        }
        return "redirect:/audiciones/listado";
    }
}
