
package com.BLCMWEB.controller;

import com.BLCMWEB.domain.GaleriaListadoDTO;
import com.BLCMWEB.service.GaleriaService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/galeria")
public class GaleriaController {

    @Autowired
    private GaleriaService galeriaService;

    @Value("${app.upload.dir:uploads/galeria}")
    private String uploadDir;

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        GaleriaListadoDTO foto = new GaleriaListadoDTO();
        foto.setIdEstado(1);
        model.addAttribute("foto", foto);
        return "galeria/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        GaleriaListadoDTO foto = galeriaService.buscarPorId(id);
        if (foto == null) {
            foto = new GaleriaListadoDTO();
        }
        model.addAttribute("foto", foto);
        return "galeria/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("foto") GaleriaListadoDTO dto,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            RedirectAttributes ra) {
        try {
            boolean esNueva = dto.getIdMedia() == null || galeriaService.buscarPorId(dto.getIdMedia()) == null;

            if (archivo != null && !archivo.isEmpty()) {
                dto.setUrl(guardarArchivo(archivo));
            } else if (!esNueva) {
                GaleriaListadoDTO actual = galeriaService.buscarPorId(dto.getIdMedia());
                dto.setUrl(actual.getUrl());
            }

            if (dto.getIdEstado() == null) {
                dto.setIdEstado(1);
            }

            if (esNueva) {
                if (!StringUtils.hasText(dto.getUrl())) {
                    ra.addFlashAttribute("error", "Debes seleccionar una imagen para la nueva foto");
                    return "redirect:/galeria/nuevo";
                }
                galeriaService.insertarFoto(dto);
            } else {
                galeriaService.actualizarFoto(dto);
            }

            ra.addFlashAttribute("todoOk", "Foto guardada correctamente");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            ra.addFlashAttribute("error", "Error al subir la imagen: " + ioEx.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al guardar la foto: " + ex.getMessage());
        }
        return "redirect:/secciones/listadoDirector";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idMedia") Integer idMedia, RedirectAttributes ra) {
        try {
            galeriaService.eliminarFoto(idMedia);
            ra.addFlashAttribute("todoOk", "Foto desactivada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al desactivar la foto");
        }
        return "redirect:/secciones/listadoDirector";
    }

    @PostMapping("/activar")
    public String activar(@RequestParam("idMedia") Integer idMedia, RedirectAttributes ra) {
        try {
            galeriaService.activarFoto(idMedia);
            ra.addFlashAttribute("todoOk", "Foto activada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al activar la foto");
        }
        return "redirect:/secciones/listadoDirector";
    }

    private String guardarArchivo(MultipartFile archivo) throws IOException {
        String original = StringUtils.cleanPath(
                archivo.getOriginalFilename() != null ? archivo.getOriginalFilename() : "imagen");

        String extension = "";
        int puntoIdx = original.lastIndexOf('.');
        if (puntoIdx >= 0) {
            extension = original.substring(puntoIdx).toLowerCase();
        }

        String nombreArchivo = UUID.randomUUID().toString().replace("-", "") + extension;

        Path directorio = Paths.get(uploadDir);
        Files.createDirectories(directorio);

        Path destino = directorio.resolve(nombreArchivo).normalize();
        archivo.transferTo(destino);

        return "/uploads/galeria/" + nombreArchivo;
    }
}
