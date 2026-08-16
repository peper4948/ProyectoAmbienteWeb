/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.BLCMWEB.service;

/**
 *
 * @author peper
 */
import com.BLCMWEB.domain.AnuncioDTO;
import com.BLCMWEB.repository.AnuncioRepository;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class AnuncioService {
 
    private final AnuncioRepository repository;
 
    public AnuncioService(AnuncioRepository repository) {
        this.repository = repository;
    }
 
    public List<AnuncioDTO> listar() {
        return repository.listar();
    }
 
    public void publicar(Integer autorCedula, String contenido) {
        if (contenido == null || contenido.isBlank()) {
            return;
        }
        repository.insertar(autorCedula, contenido.trim());
    }
    public void eliminar(Integer idAnuncio) {
    repository.eliminar(idAnuncio);
}
}
