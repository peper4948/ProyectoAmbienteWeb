package com.BLCMWEB.controller;

import com.BLCMWEB.service.SeccionRosterService;
import com.BLCMWEB.service.CorreoService;
import com.BLCMWEB.service.DireccionService;
import com.BLCMWEB.service.EstadoService;
import com.BLCMWEB.service.SeccionService;
import com.BLCMWEB.service.TelefonoService;
import com.BLCMWEB.service.UsuarioService;
import com.BLCMWEB.service.AnuncioService;
import com.BLCMWEB.service.LiderService;
import com.BLCMWEB.service.AsistenciaService;
import com.BLCMWEB.service.EnsayoService;
import com.BLCMWEB.domain.UsuarioListadoDTO;
import com.BLCMWEB.domain.LiderListadoDTO;
import com.BLCMWEB.domain.UsuarioLoginDTO;
import com.BLCMWEB.domain.EnsayoListadoDTO;
import com.BLCMWEB.domain.AsistenciaMiembroDTO;
import com.BLCMWEB.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import java.util.stream.Collectors;
import java.util.List;
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
    private SeccionRosterService seccionRosterService;

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
public String integrantes(Model model) {
    var usuarios = usuarioService.readAllUsuario();
    long totalIntegrantes = usuarios.stream()
            .filter(u -> u.getIdEstado() != null && u.getIdEstado() == 1)
            .count();

    var secciones = seccionService.readAllSeccion();
    long totalSecciones = secciones.stream()
            .filter(s -> s.getIdEstado() != null && s.getIdEstado() == 1)
            .count();

    model.addAttribute("totalIntegrantes", totalIntegrantes);
    model.addAttribute("totalSecciones", totalSecciones);
    model.addAttribute("anuncios", anuncioService.listar());
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
        model.addAttribute("ensayos", ensayoService.listarEnsayos());

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
        @RequestParam(value = "idEnsayo", required = false) Integer idEnsayoParam,
        @RequestParam(value = "idSeccion", required = false) Integer idSeccionParam) {

    UsuarioLoginDTO loginInfo = usuarioRepository.buscarPorCorreo(auth.getName());
    UsuarioListadoDTO perfil = usuarioService.buscarPorId(loginInfo.getCedula());

    boolean esAdminODirector = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_DIRECTOR"));
    model.addAttribute("esAdminODirector", esAdminODirector);

    Integer idSeccionPropia = usuarioRepository.buscarIdSeccionPorCedula(perfil.getCedula());

    // Solo Admin/Director puede cambiar de sección con ?idSeccion=X
    Integer idSeccion = (esAdminODirector && idSeccionParam != null) ? idSeccionParam : idSeccionPropia;

    if (esAdminODirector) {
        model.addAttribute("secciones", seccionService.readAllSeccion());
    }
    model.addAttribute("idSeccionSeleccionada", idSeccion);

    var seccionInfo = seccionService.buscarPorId(idSeccion);
    model.addAttribute("nombreSeccion", seccionInfo != null ? seccionInfo.getNombreSeccion() : "Sin sección");
    model.addAttribute("cedulaLider", perfil.getCedula());

    List<EnsayoListadoDTO> ensayos = ensayoService.listarEnsayos();
    model.addAttribute("ensayos", ensayos);

    Integer idEnsayoSeleccionado = idEnsayoParam != null ? idEnsayoParam
            : (ensayos.isEmpty() ? null : ensayos.get(0).getIdEnsayo());
    model.addAttribute("idEnsayoSeleccionado", idEnsayoSeleccionado);

    List<AsistenciaMiembroDTO> asistencia = (idEnsayoSeleccionado != null && idSeccion != null)
            ? asistenciaService.listarAsistencia(idSeccion, idEnsayoSeleccionado)
            : List.of();
    model.addAttribute("asistencia", asistencia);

    long presentes = asistencia.stream()
            .filter(a -> a.getIdEstadoAsistencia() != null && a.getIdEstadoAsistencia() == 3).count();
    long ausentes = asistencia.stream()
            .filter(a -> a.getIdEstadoAsistencia() != null && a.getIdEstadoAsistencia() == 4).count();
    long justificados = asistencia.stream()
            .filter(a -> a.getIdEstadoAsistencia() != null && a.getIdEstadoAsistencia() == 5).count();
    model.addAttribute("presentesHoy", presentes);
    model.addAttribute("ausentesHoy", ausentes);
    model.addAttribute("justificadosHoy", justificados);

    model.addAttribute("anuncios", idSeccion != null ? anuncioService.listarPorSeccion(idSeccion) : List.of());

    return "secciones/listadoLideres";
}

@GetMapping("/secciones/listadoPrincipales")
public String principales(Model model) {
    Long idPrincipales = 5L;   // era 8L
    model.addAttribute("integrantes", seccionRosterService.listarPorSeccion(idPrincipales));
    model.addAttribute("total", seccionRosterService.total(
            seccionRosterService.listarPorSeccion(idPrincipales)));
    model.addAttribute("totalActivos", seccionRosterService.totalActivos(
            seccionRosterService.listarPorSeccion(idPrincipales)));
    return "secciones/listadoPrincipales";
}
    @GetMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "acceso_denegado";
    }

    
    
    private void cargarSeccion(Model model, Long idSeccion) {
        var integrantes = seccionRosterService.listarPorSeccion(idSeccion);
        model.addAttribute("integrantes", integrantes);
        model.addAttribute("total", seccionRosterService.total(integrantes));
        model.addAttribute("totalActivos", seccionRosterService.totalActivos(integrantes));
    }
    
    
     @GetMapping("/secciones/listadoLiras")
    public String liras(Model model) {
        cargarSeccion(model, 1L);
        return "secciones/listadoLiras";
    }
 
    @GetMapping("/secciones/listadoBombos")
    public String bombos(Model model) {
        cargarSeccion(model, 2L);
        return "secciones/listadoBombos";
    }
 
    @GetMapping("/secciones/listadoCajas")
    public String cajas(Model model) {
        cargarSeccion(model, 3L);
        return "secciones/listadoCajas";
    }
 
    @GetMapping("/secciones/listadoTenores")
    public String tenores(Model model) {
        cargarSeccion(model, 4L);
        return "secciones/listadoTenores";
    }
 
    @GetMapping("/secciones/listadoGuiros")
    public String guiros(Model model) {
        cargarSeccion(model, 6L);
        return "secciones/listadoGuiros";
    }
 
    @GetMapping("/secciones/listadoColorGuard")
    public String colorGuard(Model model) {
        cargarSeccion(model, 7L);
        return "secciones/listadoColorGuard";
    }
 
    @GetMapping("/secciones/listadoFolclore")
    public String folclore(Model model) {
        cargarSeccion(model, 8L);
        return "secciones/listadoFolclore";
    }
    


    @PostMapping("/anuncio/publicar")
    public String publicarAnuncio(@RequestParam("contenido") String contenido,
            Authentication auth) {
        // auth.getName() = correo del usuario logueado
        var usuario = usuarioService.buscarPorCorreo(auth.getName());
        anuncioService.publicar(usuario.getCedula(), contenido);
        return "redirect:/integrantes/listado";  // ruta de tu dashboard
    }
    
      @PostMapping("/anuncio/eliminar")
    public String eliminarAnuncio(@RequestParam("idAnuncio") Integer idAnuncio) {
        anuncioService.eliminar(idAnuncio);
        return "redirect:/integrantes/listado";  // tu ruta del dashboard
    }

}


