package com.BLCMWEB.controller;

import com.BLCMWEB.domain.AudicionRequestDTO;
import com.BLCMWEB.service.AudicionService;
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

            return ResponseEntity.ok(Map.of("ok", true));
        } catch (org.springframework.dao.DataIntegrityViolationException dup) {
            return ResponseEntity.status(409).body(Map.of("ok", false,
                    "error", "Ya existe una solicitud registrada con esa cédula."));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("ok", false, "error", ex.getMessage()));
        }
    }
}
