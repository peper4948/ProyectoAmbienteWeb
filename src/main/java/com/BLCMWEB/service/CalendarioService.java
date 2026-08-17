package com.BLCMWEB.service;

import com.BLCMWEB.domain.CalendarioEvento;
import com.BLCMWEB.repository.CalendarioEventoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CalendarioService {

    @Autowired
    private CalendarioEventoRepository repository;

    public List<CalendarioEvento> listarActivos() {
        return repository.findByIdEstadoOrderByFechaAsc(1L);
    }

    public List<CalendarioEvento> listarTodos() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "fecha"));
    }

    public CalendarioEvento crear(CalendarioEvento evento) {
        evento.setIdCalendario(null);
        evento.setIdEstado(1L);
        return repository.save(evento);
    }

    public CalendarioEvento actualizar(Long id, CalendarioEvento datosNuevos) {
        CalendarioEvento evento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado: " + id));
        evento.setFecha(datosNuevos.getFecha());
        evento.setTipoEvento(datosNuevos.getTipoEvento());
        evento.setDescripcion(datosNuevos.getDescripcion());
        evento.setLugar(datosNuevos.getLugar());
        return repository.save(evento);
    }

    public void eliminar(Long id) {
        repository.findById(id).ifPresent(evento -> {
            evento.setIdEstado(0L);
            repository.save(evento);
        });
    }

    public void reactivar(Long id) {
        repository.findById(id).ifPresent(evento -> {
            evento.setIdEstado(1L);
            repository.save(evento);
        });
    }

    public Object contarTotal() {
        return repository.count();
    }

    public Object contarActivos() {
        return repository.findByIdEstadoOrderByFechaAsc(1L).size();
    }

    public void guardarEvento(CalendarioEvento evento) {
        repository.save(evento);
    }

    public CalendarioEvento obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
 
    public CalendarioEvento obtenerProximoEvento() {
        return repository.findByIdEstadoOrderByFechaAsc(1L).stream()
                .filter(e -> e.getFecha() != null && e.getFecha().isAfter(java.time.LocalDate.now()))
                .findFirst()
                .orElse(null);
    }
}
