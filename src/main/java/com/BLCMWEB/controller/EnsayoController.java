package com.BLCMWEB.controller;

import com.BLCMWEB.domain.EnsayoListadoDTO;
import com.BLCMWEB.service.EnsayoService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ensayos")
public class EnsayoController {

    @Autowired
    private EnsayoService ensayoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("ensayos", ensayoService.listarEnsayos());
        return "ensayos/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("ensayo", new EnsayoListadoDTO());
        return "ensayos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ensayo", ensayoService.buscarPorId(id));
        return "ensayos/formulario";
    }

@PostMapping("/guardar")
    public String guardar(@RequestParam(value = "idEnsayo", required = false) Integer idEnsayo,
                          @RequestParam("fecha") String fechaStr,
                          @RequestParam("lugar") String lugar,
                          RedirectAttributes ra) {
        try {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            if (idEnsayo != null) {
                ensayoService.actualizar(idEnsayo, fecha, lugar);
                ra.addFlashAttribute("todoOk", "Ensayo actualizado correctamente");
            } else {
                ensayoService.insertar(fecha, lugar);
                ra.addFlashAttribute("todoOk", "Ensayo creado correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al guardar el ensayo: " + ex.getMessage());
        }
        return "redirect:/secciones/listadoDirector";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idEnsayo") Integer idEnsayo, RedirectAttributes ra) {
        try {
            ensayoService.eliminar(idEnsayo);
            ra.addFlashAttribute("todoOk", "Ensayo eliminado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al eliminar el ensayo");
        }
        return "redirect:/secciones/listadoDirector"; 
    }
}