package com.BLCMWEB.controller;

import com.BLCMWEB.domain.AudicionRequestDTO;
import com.BLCMWEB.domain.SeccionListadoDTO;
import com.BLCMWEB.service.AudicionService;
import com.BLCMWEB.service.EmailService;
import com.BLCMWEB.service.SeccionService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audiciones")
public class AudicionController {

    @Autowired
    private AudicionService audicionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SeccionService seccionService;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody AudicionRequestDTO dto) {
        try {
            if (dto.getCedula() == null
                    || dto.getNombre() == null || dto.getNombre().isBlank()
                    || dto.getApellidoPaterno() == null || dto.getApellidoPaterno().isBlank()
                    || dto.getEmail() == null || dto.getEmail().isBlank()
                    || dto.getTelefono() == null || dto.getTelefono().isBlank()
                    || dto.getIdSeccion() == null) {
                return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Faltan datos obligatorios"));
            }

            audicionService.registrarAudicion(
                    dto.getCedula(),
                    dto.getNombre().trim(),
                    dto.getApellidoPaterno().trim(),
                    dto.getApellidoMaterno() != null ? dto.getApellidoMaterno().trim() : null,
                    dto.getEmail().trim(),
                    dto.getTelefono().trim(),
                    dto.getIdSeccion(),
                    dto.getComentarios()
            );

            // Buscar el nombre real de la sección para el correo
            SeccionListadoDTO seccion = seccionService.buscarPorId(dto.getIdSeccion());
            String nombreSeccion = seccion != null ? seccion.getNombreSeccion() : "N/A";

            try {
                String apellidos = dto.getApellidoPaterno()
                        + (dto.getApellidoMaterno() != null ? " " + dto.getApellidoMaterno() : "");
                emailService.enviarConfirmacionAudicion(
                        dto.getEmail().trim(),
                        dto.getNombre().trim(),
                        apellidos,
                        nombreSeccion,
                        dto.getTelefono().trim(),
                        dto.getCedula(),
                        dto.getComentarios()
                );
            } catch (Exception mailEx) {
                mailEx.printStackTrace();
            }

            return ResponseEntity.ok(Map.of("ok", true));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("ok", false, "error", ex.getMessage()));
        }
    }
}
