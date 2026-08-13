package com.BLCMWEB.controller;

import com.BLCMWEB.service.LiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.BLCMWEB.service.AsistenciaService;
import com.BLCMWEB.service.AnuncioService;
import java.util.Map;

@Controller
@RequestMapping("/lider")
public class LiderController {

    @Autowired
    private LiderService liderService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AnuncioService anuncioService;

    @PostMapping("/asignar")
    public String asignar(@RequestParam("cedula") Integer cedula, RedirectAttributes ra) {
        try {
            liderService.asignarLider(cedula);
            ra.addFlashAttribute("todoOk", "Se asignó el rol de Líder de Sección correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al asignar el rol de líder: " + ex.getMessage());
        }
        return "redirect:/secciones/listadoDirector";
    }

    @PostMapping("/quitar")
    public String quitar(@RequestParam("cedula") Integer cedula, RedirectAttributes ra) {
        try {
            liderService.quitarLider(cedula);
            ra.addFlashAttribute("todoOk", "Se removió el rol de Líder correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al quitar el rol de líder");
        }
        return "redirect:/secciones/listadoDirector";
    }

    @PostMapping("/asistencia/guardar")
    public String guardarAsistencia(@RequestParam("idEnsayo") Integer idEnsayo,
            @RequestParam Map<String, String> params,
            RedirectAttributes ra) {
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey().startsWith("asistencia_")) {
                    Integer cedula = Integer.valueOf(entry.getKey().replace("asistencia_", ""));
                    Integer idEstado = Integer.valueOf(entry.getValue());
                    asistenciaService.guardarAsistencia(idEnsayo, cedula, idEstado);
                }
            }
            ra.addFlashAttribute("todoOk", "Asistencia guardada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al guardar la asistencia");
        }
        return "redirect:/secciones/listadoLideres?idEnsayo=" + idEnsayo;
    }

    @PostMapping("/anuncio/guardar")
    public String guardarAnuncio(@RequestParam("cedula") Integer cedula,
            @RequestParam("contenido") String contenido,
            RedirectAttributes ra) {
        try {
            anuncioService.insertarAnuncio(cedula, contenido);
            ra.addFlashAttribute("todoOk", "Anuncio publicado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al publicar el anuncio");
        }
        return "redirect:/secciones/listadoLideres";
    }

    @PostMapping("/anuncio/eliminar")
    public String eliminarAnuncio(@RequestParam("idAnuncio") Integer idAnuncio, RedirectAttributes ra) {
        try {
            anuncioService.eliminarAnuncio(idAnuncio);
            ra.addFlashAttribute("todoOk", "Anuncio eliminado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al eliminar el anuncio");
        }
        return "redirect:/secciones/listadoLideres";
    }
}
