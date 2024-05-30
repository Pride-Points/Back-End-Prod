package com.pridepoints.api.repositories;

import com.pridepoints.api.entities.Avaliacao;
import com.pridepoints.api.entities.Fisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByEmpresaId(Long id);

    List<Avaliacao> findByPessoaFisicaId(Long idUsuario);

    Integer countByPessoaFisicaId(Long idUsuario);

}
