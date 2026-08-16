package com.BLCMWEB.service;

import com.BLCMWEB.domain.AnuncioDTO;
import com.BLCMWEB.domain.AnuncioListadoDTO;
import com.BLCMWEB.repository.AnuncioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    // ===== Dashboard (todos los anuncios) =====
    public List<AnuncioDTO> listar() {
        return anuncioRepository.listar();
    }

    public void publicar(Integer autorCedula, String contenido) {
        if (contenido == null || contenido.isBlank()) {
            return;
        }
        anuncioRepository.insertar(autorCedula, contenido.trim());
    }

    public void eliminar(Integer idAnuncio) {
        anuncioRepository.eliminar(idAnuncio);
    }

    // ===== Panel de líderes (anuncios por sección) =====
    public List<AnuncioListadoDTO> listarPorSeccion(Integer idSeccion) {
        return anuncioRepository.listarPorSeccion(idSeccion);
    }

    public void insertarAnuncio(Integer autorCedula, Integer idSeccion, String contenido) {
        anuncioRepository.insertarAnuncio(autorCedula, idSeccion, contenido);
    }

    public void eliminarAnuncio(Integer idAnuncio) {
        anuncioRepository.eliminarAnuncio(idAnuncio);
    }
}
