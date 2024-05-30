package com.pridepoints.api.repositories;

import com.pridepoints.api.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByEmpresaId(Long idEmpresa);

    Optional<Evento> findByEmpresaIdAndId(Long idEmpresa, Long idEvento);
}
