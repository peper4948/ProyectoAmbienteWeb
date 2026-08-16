package com.BLCMWEB.service;

import com.BLCMWEB.domain.LiderListadoDTO;
import com.BLCMWEB.repository.LiderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiderService {

    @Autowired
    private LiderRepository liderRepository;

    public List<LiderListadoDTO> listarLideres() {
        return liderRepository.listarLideres();
    }

    public void asignarLider(Integer cedula) {
        liderRepository.asignarLider(cedula);
    }

    public void quitarLider(Integer cedula) {
        liderRepository.quitarLider(cedula);
    }
}