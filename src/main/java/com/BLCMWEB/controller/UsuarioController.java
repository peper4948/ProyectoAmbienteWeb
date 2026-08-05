package com.BLCMWEB.controller;

import com.BLCMWEB.domain.Correo;
import com.BLCMWEB.domain.Telefono;
import com.BLCMWEB.domain.Usuarios;
import com.BLCMWEB.domain.UsuarioListadoDTO;
import com.BLCMWEB.domain.UsuarioLoginDTO;
import com.BLCMWEB.repository.UsuarioRepository;
import com.BLCMWEB.service.CorreoService;
import com.BLCMWEB.service.DireccionService;
import com.BLCMWEB.service.EstadoService;
import com.BLCMWEB.service.SeccionService;
import com.BLCMWEB.service.TelefonoService;
import com.BLCMWEB.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @GetMapping("/listado")
    public String listado(Model model) {
        List<UsuarioListadoDTO> lista = usuarioService.readAllUsuario();
        model.addAttribute("usuarios", lista);
        model.addAttribute("nuevoUsuario", new UsuarioListadoDTO());
        cargarCombos(model);
        return "usuario/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new UsuarioListadoDTO());
        cargarCombos(model);
        return "usuario/formulario";
    }

    @GetMapping("/editar/{cedula}")
    public String editar(@PathVariable("cedula") Integer cedula, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(cedula));
        cargarCombos(model);
        return "usuario/formulario";
    }

@PostMapping("/guardar")
    public String guardar(@ModelAttribute UsuarioListadoDTO dto, 
                          RedirectAttributes ra, 
                          jakarta.servlet.http.HttpServletRequest request) {
        try {
            // 1. Guardar el Correo primero y capturar su ID generado
            Integer idCorreo = null;
            if (dto.getCorreo() != null && !dto.getCorreo().isEmpty()) {
                Correo correo = new Correo();
                correo.setCorreo(dto.getCorreo()); 
                correoService.insertarCorreo(correo); 
                idCorreo = correo.getIdCorreo(); 
            }

            // 2. Guardar el Teléfono primero y capturar su ID generado
            Integer idTelefono = null;
            if (dto.getTelefono() != null && !dto.getTelefono().isEmpty()) {
                Telefono telefono = new Telefono();
                telefono.setTelefono(dto.getTelefono());
                telefonoService.insertarTelefono(telefono);
                idTelefono = telefono.getIdTelefono();
            }

            // 3. Crear el objeto de Usuario vinculando las llaves foráneas
            Usuarios usuario = new Usuarios();
            usuario.setCedula(dto.getCedula());
            usuario.setNombre(dto.getNombre());
            usuario.setPrimerApellido(dto.getApellidoPaterno());
            usuario.setSegundoApellido(dto.getApellidoMaterno());
            usuario.setIdSeccion(dto.getIdSeccion());
            usuario.setIdEstado(dto.getIdEstado());
            usuario.setIdCorreo(idCorreo);
            usuario.setIdTelefono(idTelefono);
            
            // ¡IMPORTANTE! Enviamos explícitamente null a la dirección para cumplir con Oracle
            usuario.setIdDireccion(null); 
            
            // 4. Insertar o actualizar el usuario y manejar la encriptación
            if (usuarioService.buscarPorId(dto.getCedula()) != null) {
                // Si existe, lo actualizamos (la contraseña no se toca en este form normal)
                usuarioService.actualizarUsuario(usuario);
            } else {
                // Si es nuevo, aplicamos la magia de la contraseña:
                if (dto.getClave() == null || dto.getClave().isEmpty()) {
                    String claveEncriptada = passwordEncoder.encode(String.valueOf(dto.getCedula()));
                    usuario.setClave(claveEncriptada);
                } else {
                    usuario.setClave(passwordEncoder.encode(dto.getClave()));
                }
                
                // 1. Insertamos el usuario en Oracle
                usuarioService.insertarUsuario(usuario);
                
                // 2. ¡NUEVO! Le asignamos el ROL DE INTEGRANTE (ID_ROL = 3) por defecto
                usuarioService.asignarRol(usuario.getCedula(), 3);
            }

            ra.addFlashAttribute("todoOk", "Usuario guardado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al guardar: " + ex.getMessage());
        }

        return "redirect:/secciones/listadoDirector";
    }
@PostMapping("/eliminar")
    public String eliminar(@RequestParam("cedula") Integer cedula, RedirectAttributes ra) {
        try {
            usuarioService.eliminarUsuario(cedula);
            ra.addFlashAttribute("todoOk", "Usuario eliminado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar");
        }
        
        return "redirect:/secciones/listadoDirector";
    }

    private void cargarCombos(Model model) {
        model.addAttribute("telefonos", telefonoService.readAllTelefono());
        model.addAttribute("correos", correoService.readAllCorreo());
        model.addAttribute("secciones", seccionService.readAllSeccion());
        model.addAttribute("direcciones", direccionService.readAllDireccion());
        model.addAttribute("estados", estadoService.readAllEstado());
    }
    
    @GetMapping("/cambiarPassword")
    public String cambiarPasswordForm() {
        return "usuario/cambiarPassword";
    }

    @PostMapping("/cambiarPassword")
    public String cambiarPassword(@RequestParam("actual") String actual,
                                  @RequestParam("nueva") String nueva,
                                  @RequestParam("confirmar") String confirmar,
                                  Authentication auth,
                                  RedirectAttributes ra) {
        try {
            UsuarioLoginDTO usuario = usuarioRepository.buscarPorCorreo(auth.getName());
            if (usuario == null) {
                ra.addFlashAttribute("error", "No se encontro el usuario");
                return "redirect:/usuario/cambiarPassword";
            }

            if (!passwordEncoder.matches(actual, usuario.getClave())) {
                ra.addFlashAttribute("error", "La contrasena actual no es correcta");
                return "redirect:/usuario/cambiarPassword";
            }

            if (!nueva.equals(confirmar)) {
                ra.addFlashAttribute("error", "La nueva contrasena y su confirmacion no coinciden");
                return "redirect:/usuario/cambiarPassword";
            }

            if (nueva.length() < 8) {
                ra.addFlashAttribute("error", "La contrasena debe tener al menos 8 caracteres");
                return "redirect:/usuario/cambiarPassword";
            }

            if (nueva.equals(String.valueOf(usuario.getCedula()))) {
                ra.addFlashAttribute("error", "No podes usar tu cedula como contrasena");
                return "redirect:/usuario/cambiarPassword";
            }

            usuarioService.cambiarPassword(usuario.getCedula(), passwordEncoder.encode(nueva));
            ra.addFlashAttribute("todoOk", "Contrasena actualizada correctamente");
            return "redirect:/banda/listado";

        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error al cambiar la contrasena");
            return "redirect:/usuario/cambiarPassword";
        }
    }
}