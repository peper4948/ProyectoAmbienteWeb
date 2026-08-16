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

    public List<EnsayoListadoDTO> listarEnsayos() {
        return ensayoRepository.listarEnsayos();
    }
}