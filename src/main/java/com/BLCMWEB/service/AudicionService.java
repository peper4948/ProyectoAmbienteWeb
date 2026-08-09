package com.BLCMWEB.service;

import com.BLCMWEB.domain.AudicionListadoDTO;
import com.BLCMWEB.repository.AudicionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudicionService {

    @Autowired
    private AudicionRepository audicionRepository;

    public void registrarAudicion(Long cedula, String nombre, String apellidoPaterno, String apellidoMaterno,
            String email, String telefono, Integer idSeccion, String comentarios) {
        audicionRepository.insertarAudicion(cedula, nombre, apellidoPaterno, apellidoMaterno,
                email, telefono, idSeccion, comentarios);
    }

    public List<AudicionListadoDTO> readAllAudiciones() {
        return audicionRepository.readAllAudiciones();
    }

    public void cambiarEstado(Integer idAudicion, Integer idEstado) {
        audicionRepository.actualizarEstado(idAudicion, idEstado);
    }
}
