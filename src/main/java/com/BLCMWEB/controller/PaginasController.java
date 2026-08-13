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
import com.BLCMWEB.domain.LiderListadoDTO;
import com.BLCMWEB.service.LiderService;
import java.util.stream.Collectors;
import com.BLCMWEB.domain.UsuarioLoginDTO;
import com.BLCMWEB.domain.EnsayoListadoDTO;
import com.BLCMWEB.domain.AsistenciaMiembroDTO;
import com.BLCMWEB.repository.UsuarioRepository;
import com.BLCMWEB.service.AnuncioService;
import com.BLCMWEB.service.AsistenciaService;
import com.BLCMWEB.service.EnsayoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
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

    @Autowired
    private LiderService liderService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnsayoService ensayoService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AnuncioService anuncioService;

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
    public String audiciones(Model model) {
        var secciones = seccionService.readAllSeccion();
        model.addAttribute("secciones", secciones);
        model.addAttribute("hayAbiertas", secciones.stream().anyMatch(s -> s.getIdEstado() != null && s.getIdEstado() == 1));
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

        var usuarios = usuarioService.readAllUsuario();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nuevoUsuario", new UsuarioListadoDTO());
        model.addAttribute("telefonos", telefonoService.readAllTelefono());
        model.addAttribute("correos", correoService.readAllCorreo());
        model.addAttribute("secciones", seccionService.readAllSeccion());
        model.addAttribute("direcciones", direccionService.readAllDireccion());
        model.addAttribute("estados", estadoService.readAllEstado());

        var lideres = liderService.listarLideres();
        model.addAttribute("lideres", lideres);

        var cedulasLideresActivos = lideres.stream()
                .filter(l -> l.getIdEstado() != null && l.getIdEstado() == 1)
                .map(LiderListadoDTO::getCedula)
                .collect(Collectors.toSet());

        var integrantesDisponibles = usuarios.stream()
                .filter(u -> u.getIdEstado() != null && u.getIdEstado() == 1)
                .filter(u -> !cedulasLideresActivos.contains(u.getCedula()))
                .toList();
        model.addAttribute("integrantesDisponibles", integrantesDisponibles);

        return "secciones/listadoDirector";
    }

    @GetMapping("/secciones/listadoLideres")
    public String lideres(Model model, Authentication auth,
            @RequestParam(value = "idEnsayo", required = false) Integer idEnsayoParam) {

        UsuarioLoginDTO loginInfo = usuarioRepository.buscarPorCorreo(auth.getName());
        UsuarioListadoDTO perfil = usuarioService.buscarPorId(loginInfo.getCedula());

        Integer idSeccion = usuarioRepository.buscarIdSeccionPorCedula(perfil.getCedula());
        model.addAttribute("nombreSeccion", perfil.getNombreSeccion());
        model.addAttribute("cedulaLider", perfil.getCedula());

        List<EnsayoListadoDTO> ensayos = ensayoService.listarEnsayos();
        model.addAttribute("ensayos", ensayos);

        Integer idEnsayoSeleccionado = idEnsayoParam != null ? idEnsayoParam
                : (ensayos.isEmpty() ? null : ensayos.get(0).getIdEnsayo());
        model.addAttribute("idEnsayoSeleccionado", idEnsayoSeleccionado);

        List<AsistenciaMiembroDTO> asistencia = idEnsayoSeleccionado != null
                ? asistenciaService.listarAsistencia(idSeccion, idEnsayoSeleccionado)
                : List.of();
        model.addAttribute("asistencia", asistencia);

        long presentes = asistencia.stream()
                .filter(a -> a.getIdEstadoAsistencia() != null && a.getIdEstadoAsistencia() == 20).count();
        long ausentes = asistencia.stream()
                .filter(a -> a.getIdEstadoAsistencia() != null && a.getIdEstadoAsistencia() == 21).count();
        long justificados = asistencia.stream()
                .filter(a -> a.getIdEstadoAsistencia() != null && a.getIdEstadoAsistencia() == 22).count();
        model.addAttribute("presentesHoy", presentes);
        model.addAttribute("ausentesHoy", ausentes);
        model.addAttribute("justificadosHoy", justificados);

        model.addAttribute("anuncios", anuncioService.listarPorSeccion(idSeccion));

        return "secciones/listadoLideres";
    }

    @GetMapping("/secciones/listadoPrincipales")
    public String principales() {
        return "secciones/listadoPrincipales";
    }

    @GetMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "acceso_denegado";
    }

}
