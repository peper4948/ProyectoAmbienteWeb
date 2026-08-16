
package com.BLCMWEB.service;

import com.BLCMWEB.domain.GaleriaListadoDTO;
import com.BLCMWEB.repository.GaleriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GaleriaService {

    @Autowired
    private GaleriaRepository galeriaRepository;

    public List<GaleriaListadoDTO> listarTodas() {
        return galeriaRepository.listarTodas();
    }

    public List<GaleriaListadoDTO> listarActivas() {
        return galeriaRepository.listarActivas();
    }

    public GaleriaListadoDTO buscarPorId(Integer idMedia) {
        return galeriaRepository.buscarPorId(idMedia);
    }

    public void insertarFoto(GaleriaListadoDTO dto) {
        galeriaRepository.insertarFoto(dto);
    }

    public void actualizarFoto(GaleriaListadoDTO dto) {
        galeriaRepository.actualizarFoto(dto);
    }

    public void eliminarFoto(Integer idMedia) {
        galeriaRepository.cambiarEstado(idMedia, 2);
    }

    public void activarFoto(Integer idMedia) {
        galeriaRepository.cambiarEstado(idMedia, 1);
    }
}
