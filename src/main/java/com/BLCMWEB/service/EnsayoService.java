package com.BLCMWEB.service;

import com.BLCMWEB.domain.EnsayoListadoDTO;
import com.BLCMWEB.repository.EnsayoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnsayoService {

    @Autowired
    private EnsayoRepository ensayoRepository;

    // Listar (de tu compañero) - intacto
    public List<EnsayoListadoDTO> listarEnsayos() {
        return ensayoRepository.listarEnsayos();
    }

    public EnsayoListadoDTO buscarPorId(Integer idEnsayo) {
        return ensayoRepository.buscarPorId(idEnsayo);
    }

    public void insertar(java.util.Date fecha, String lugar) {
        ensayoRepository.insertar(fecha, lugar);
    }

    public void actualizar(Integer idEnsayo, java.util.Date fecha, String lugar) {
        ensayoRepository.actualizar(idEnsayo, fecha, lugar);
    }

    public void eliminar(Integer idEnsayo) {
        ensayoRepository.eliminar(idEnsayo);
    }
}