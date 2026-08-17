package com.BLCMWEB.repository;

import com.BLCMWEB.domain.CalendarioEvento;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioEventoRepository extends JpaRepository<CalendarioEvento, Long> {

    // Búsqueda por rango de fechas (el nombre de entidad en el JPQL debe ser CalendarioEvento)
    @Query("SELECT e FROM CalendarioEvento e WHERE e.fecha BETWEEN :inicio AND :fin")
    List<CalendarioEvento> findByFechaBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );

    List<CalendarioEvento> findByIdCalendario(Long idCalendario);

    List<CalendarioEvento> findByIdEstado(Long idEstado);

    List<CalendarioEvento> findByIdEstadoOrderByFechaAsc(Long idEstado);
}