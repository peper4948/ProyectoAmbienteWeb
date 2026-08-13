package com.BLCMWEB.service;

import com.BLCMWEB.domain.AsistenciaMiembroDTO;
import com.BLCMWEB.repository.AsistenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public List<AsistenciaMiembroDTO> listarAsistencia(Integer idSeccion, Integer idEnsayo) {
        return asistenciaRepository.listarAsistenciaPorEnsayo(idSeccion, idEnsayo);
    }

    public void guardarAsistencia(Integer idEnsayo, Integer cedula, Integer idEstadoAsistencia) {
        asistenciaRepository.guardarAsistencia(idEnsayo, cedula, idEstadoAsistencia);
    }
}