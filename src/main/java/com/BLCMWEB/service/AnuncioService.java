package com.BLCMWEB.service;

import com.BLCMWEB.domain.AnuncioListadoDTO;
import com.BLCMWEB.repository.AnuncioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    public List<AnuncioListadoDTO> listarPorSeccion(Integer idSeccion) {
        return anuncioRepository.listarPorSeccion(idSeccion);
    }

    public void insertarAnuncio(Integer autorCedula, String contenido) {
        anuncioRepository.insertarAnuncio(autorCedula, contenido);
    }

    public void eliminarAnuncio(Integer idAnuncio) {
        anuncioRepository.eliminarAnuncio(idAnuncio);
    }
}