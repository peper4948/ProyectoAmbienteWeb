package com.BLCMWEB.service;

import com.BLCMWEB.domain.IntegrantesSeccionDTO;          // singular: Integrante
import com.BLCMWEB.repository.SeccionRoasterRepository; // Roster, no Roaster
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeccionRosterService {

    private final SeccionRoasterRepository repository;   // Roster

    public SeccionRosterService(SeccionRoasterRepository repository) {  // Roster
        this.repository = repository;
    }

    public List<IntegrantesSeccionDTO> listarPorSeccion(Long idSeccion) {   // Integrante
        return repository.listarPorSeccion(idSeccion);
    }

    public long total(List<IntegrantesSeccionDTO> integrantes) {   // Integrante
        return integrantes.size();
    }

    public long totalActivos(List<IntegrantesSeccionDTO> integrantes) {   // Integrante
        return integrantes.stream().filter(IntegrantesSeccionDTO::isActivo).count();
    }
}